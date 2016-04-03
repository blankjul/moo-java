package com.msu.moo.algorithms.impl.single;

import java.util.HashSet;
import java.util.List;

import com.msu.moo.algorithms.ASingleObjectiveAlgorithm;
import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IFactory;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.interfaces.ISelection;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.ASingleObjectiveProblem;
import com.msu.moo.model.solution.SingleObjectiveComparator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.selection.RandomSelection;
import com.msu.moo.util.MyRandom;

public class SingleObjectiveEvolutionaryAlgorithm<V extends IVariable, P extends ASingleObjectiveProblem<V>>
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
	
	//! if true output is printed
	protected boolean verbose = false;
	
	//! final population when the algorithm was executed
	protected SolutionSet<ISolution<V>> population = null;
	


	@Override
	public ISolution<V> run(P problem, IEvaluator evaluator, MyRandom rand) {
	
		// initial population
		if (population == null || population.isEmpty()) 
			population = initialize(problem, evaluator, rand);
		

		// iterate until evaluator has no evaluations
		while (evaluator.hasNext()) {
			population = next(problem, evaluator, rand, population);
			if (verbose) SingleObjectiveEvolutionaryAlgorithm.print(population, 3);
		}
		
		// return the best
		return population.get(0);
		
	}
	
	public SolutionSet<ISolution<V>> initialize(P problem, IEvaluator evaluator, MyRandom rand) {
		population = new SolutionSet<>();
		while (population.size() < populationSize) {
			population.add(evaluator.evaluate(problem, factory.next(rand)));
		}
		
		return population;
	}
	
	
	public SolutionSet<ISolution<V>> next(P problem, IEvaluator evaluator, MyRandom rand, SolutionSet<ISolution<V>> population) {
		
		// mating with random selection of the best 20 percent
		SolutionSet<ISolution<V>> offsprings = new SolutionSet<>(populationSize);

		// selects per default always the maximal value
		//ASelection<V> selector = new SingleObjectiveBinaryTournament<>(population, rand);
		ISelection<ISolution<V>> selector = new RandomSelection<>();

		while (offsprings.size() < populationSize) {
			
			// crossover
			SolutionSet<ISolution<V>> selected = selector.next(population, 2, rand);
			
			List<V> off = crossover.crossover(selected.get(0).getVariable(), selected.get(1).getVariable(), rand);

			// mutation
			for (V offspring : off) {
				if (rand.nextDouble() < this.probMutation) {
					mutation.mutate(offspring, rand);
				}
				
				// evaluate directly or perform local optimization
				ISolution<V> s = evaluator.evaluate(problem, offspring);
					
				offsprings.add(s);
			}
		}
		population.addAll(offsprings);

		// eliminate duplicates to ensure variety in the population
		SolutionSet<ISolution<V>> next = new SolutionSet<>(new HashSet<>(population));
		
		// truncate the population -> survival of the fittest
		next.sort(new SingleObjectiveComparator());
		
		next = new SolutionSet<ISolution<V>>(next.subList(0, Math.min(next.size(), populationSize)));
		
		evaluator.notify(next);
		
		
		
		return next;
	}

	
	public SolutionSet<ISolution<V>> getPopulation() {
		return population;
	}
	
	
	public static <V> void print(SolutionSet<ISolution<V>> population, int n) {
		for (ISolution<V> solution : population.subList(0, Math.min(n, population.size()))) {
			System.out.println(solution);
		}
		System.out.println("-----------------------------------");
	}

}
