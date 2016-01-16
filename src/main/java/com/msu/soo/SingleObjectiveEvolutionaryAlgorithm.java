package com.msu.soo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.ILocalOptimization;
import com.msu.interfaces.IMutation;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.ISolution;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.ASingleObjectiveAlgorithm;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.util.MyRandom;

public class SingleObjectiveEvolutionaryAlgorithm<V extends IVariable, P extends IProblem<V>>
		extends ASingleObjectiveAlgorithm<V, P> {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! operator for crossover
	protected ICrossover<V> crossover;

	// ! operator for mutation
	protected IMutation<V> mutation;

	// ! factory for creating new instances
	protected IFactory<V> factory;
	
	// ! factory for creating new instances
	protected ILocalOptimization<V, P> local;
	
	//! if true output is printed
	protected boolean verbose = false;
	

	public static Comparator<ISolution<?>> comp = new Comparator<ISolution<?>>() {
		@Override
		public int compare(ISolution<?> o1, ISolution<?> o2) {
			int constraint = Double.compare(o1.getSumOfConstraintViolation(), o2.getSumOfConstraintViolation());
			if (constraint != 0)
				return constraint;
			else {
				for (int i = 0; i < o1.numOfObjectives(); i++) {
					int value = Double.compare(o1.getObjective(i), o2.getObjective(i));
					if (value != 0)
						return value;
				}
				return 0;
			}
		}
	};



	@Override
	public Solution<V> run(P problem, IEvaluator evaluator, MyRandom rand) {
	
		// initial population
		SolutionSet<V> population = initialize(problem, evaluator, rand);

		// iterate until evaluator has no evaluations
		while (evaluator.hasNext()) {
			population = next(problem, evaluator, rand, population);
		}
		
		// return the best
		return population.get(0);
		
	}
	
	public SolutionSet<V> initialize(P problem, IEvaluator evaluator, MyRandom rand) {
		SolutionSet<V> population = new SolutionSet<>();
		while (population.size() < populationSize) {
			population.add(evaluator.evaluate(problem, factory.next(rand)));
		}
		return population;
	}
	
	
	public SolutionSet<V> next(P problem, IEvaluator evaluator, MyRandom rand, SolutionSet<V> population) {
		
		// mating with random selection of the best 20 percent
		SolutionSet<V> offsprings = new SolutionSet<V>(populationSize);

		// selects per default always the maximal value
		BinaryTournamentSelection<V> selector = new BinaryTournamentSelection<V>(population,
				new Comparator<Solution<V>>() {
					@Override
					public int compare(Solution<V> o1, Solution<V> o2) {
						return -1 * comp.compare(o1, o2);
					}
				}, rand);

		while (offsprings.size() < populationSize) {
			// crossover
			List<V> off = crossover.crossover(selector.next().getVariable(), selector.next().getVariable(), rand);

			// mutation
			for (V offspring : off) {
				if (rand.nextDouble() < this.probMutation) {
					mutation.mutate(offspring, rand);
				}
				
				// evaluate directly or perform local optimization
				Solution<V> s = null;
				if (local != null && evaluator.hasNext()) {
					s = local.run(problem, evaluator, offspring);
				} else {
					s = evaluator.evaluate(problem, offspring);
				}
					
				offsprings.add(s);
			}
		}
		population.addAll(offsprings);

		// eliminate duplicates to ensure variety in the population
		SolutionSet<V> next = new SolutionSet<V>(new HashSet<>(population));
		// truncate the population -> survival of the fittest
		next.sort(comp);
		next = new SolutionSet<V>(next.subList(0, Math.min(next.size(), populationSize)));
		
		if (verbose) {
			for (Solution<V> solution : next.subList(0, 3)) {
				System.out.println(solution);
			}
			System.out.println("-----------------------------------");
		}
		
		return next;
	}


}
