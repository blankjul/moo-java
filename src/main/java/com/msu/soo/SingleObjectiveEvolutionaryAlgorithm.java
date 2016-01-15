package com.msu.soo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IMutation;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.ISingleObjectiveAlgorithm;
import com.msu.interfaces.ISolution;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.util.MyRandom;

public class SingleObjectiveEvolutionaryAlgorithm<V extends IVariable, P extends IProblem<V>>  implements ISingleObjectiveAlgorithm<V,P> {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! operator for crossover
	protected ICrossover<V,P> crossover;

	// ! operator for mutation
	protected IMutation<V,P> mutation;

	// ! factory for creating new instances
	protected IFactory<V,P> factory;
	
	// ! population of the last run
	protected SolutionSet<V> population;

	
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
	public Solution<V> run(P problem, IEvaluator<V,P> evaluator, MyRandom rand) {
			
		while (population.size() < populationSize) {
			population.add(evaluator.evaluate(problem, factory.next(problem, rand)));
		}
		
		while (evaluator.hasNext()) {
			
			// mating with random selection of the best 20 percent
			SolutionSet<V> offsprings = new SolutionSet<V>(populationSize);

			// selects per default always the maximal value
			BinaryTournamentSelection<V> selector = new BinaryTournamentSelection<V>(population, new Comparator<Solution<V>>() {
				@Override
				public int compare(Solution<V> o1, Solution<V> o2) {
					return -1 * comp.compare(o1, o2);
				}
			}, rand);

			while (offsprings.size() < populationSize) {
				// crossover
				List<V> off = crossover.crossover(problem, rand, selector.next().getVariable(), selector.next().getVariable());
				
				// mutation
				for (V offspring : off) {
					if (rand.nextDouble() < this.probMutation) {
						mutation.mutate(problem, rand, offspring);
					}
					offsprings.add(evaluator.evaluate(problem, offspring));
				}
			}
			population.addAll(offsprings);

			// eliminate duplicates to ensure variety in the population
			population = new SolutionSet<V>(new HashSet<>(population));
			// truncate the population -> survival of the fittest
			population.sort(comp);
			population = new SolutionSet<V>(population.subList(0, Math.min(population.size(), populationSize)));
			
		}
		return population.get(0);
	}

	


	public SolutionSet<V> getPopulation() {
		return population;
	}
	
	public void setPopulation(SolutionSet<V> population) {
		this.population = population;
	}



	

}
