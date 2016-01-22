package com.msu.moo.algorithms.single;

import java.util.HashSet;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IFactory;
import com.msu.moo.interfaces.ILocalOptimization;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.ASelection;
import com.msu.moo.model.ASingleObjectiveAlgorithm;
import com.msu.moo.model.solution.SingleObjectiveComparator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.MyRandom;

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
	
	//! final population when the algorithm was executed
	protected SolutionSet<V> population = null;
	


	@Override
	public Solution<V> run(P problem, IEvaluator evaluator, MyRandom rand) {
	
		// initial population
		if (population == null || population.isEmpty()) 
			population = initialize(problem, evaluator, rand);
		
		if (verbose) SingleObjectiveEvolutionaryAlgorithm.print(population, 100);
	

		// iterate until evaluator has no evaluations
		while (evaluator.hasNext()) {
			population = next(problem, evaluator, rand, population);
			if (verbose) SingleObjectiveEvolutionaryAlgorithm.print(population, 100);
		}
		
		// return the best
		return population.get(0);
		
	}
	
	public SolutionSet<V> initialize(P problem, IEvaluator evaluator, MyRandom rand) {
		population = new SolutionSet<>();
		while (population.size() < populationSize) {
			population.add(evaluator.evaluate(problem, factory.next(rand)));
		}
		return population;
	}
	
	
	public SolutionSet<V> next(P problem, IEvaluator evaluator, MyRandom rand, SolutionSet<V> population) {
		
		// mating with random selection of the best 20 percent
		SolutionSet<V> offsprings = new SolutionSet<V>(populationSize);

		// selects per default always the maximal value
		ASelection<V> selector = new SingleObjectiveBinaryTournament<>(population, rand);

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
		next.sort(new SingleObjectiveComparator());
		next = new SolutionSet<V>(next.subList(0, Math.min(next.size(), populationSize)));
		
		evaluator.notify(next);
		
		
		return next;
	}

	
	public SolutionSet<V> getPopulation() {
		return population;
	}
	
	
	public static <V> void print(SolutionSet<V> population, int n) {
		for (Solution<V> solution : population.subList(0, Math.min(n, population.size()))) {
			System.out.println(solution);
		}
		System.out.println("-----------------------------------");
	}

}
