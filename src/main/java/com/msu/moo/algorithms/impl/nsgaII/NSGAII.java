package com.msu.moo.algorithms.impl.nsgaII;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IFactory;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISelection;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.sorting.SortingBestOrder;
import com.msu.moo.util.IListener;
import com.msu.moo.util.MyRandom;

public class NSGAII<V extends IVariable, P extends IProblem<V>> extends AMultiObjectiveAlgorithm<V, P> {

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

	// ! selector for getting parents from the population
	protected ISelection<NSGAIISolution<V>> selector;

	// ! allow external listener to get updates
	protected IListener<SolutionSet<NSGAIISolution<V>>> listener;

	// population with NSGAII solutions -> allows to save rank and crowding
	// directly
	protected SolutionSet<NSGAIISolution<V>> population = null;

	// recommended for discrete problems
	protected boolean elimanateDuplicates = false;

	// ! private constructor! use the builder!
	protected NSGAII() {
	}

	@Override
	public NonDominatedSet<ISolution<V>> run_(P problem, IEvaluator evaluator, MyRandom rand) {

		// initialize the population and calculate also rank and crowding
		population = new SolutionSet<>(populationSize * 2);

		for (int i = 0; i < populationSize; i++) {
			ISolution<V> s = evaluator.evaluate(problem, factory.next(rand));
			population.add(new NSGAIISolution<>(s));
		}

		// calculate Rank and Crowding for the initial population
		// it is necessary for the BinaryTournamentSelection
		calcRankAndCrowding(population);
		
		// notify the listener
		if (listener != null) listener.notify(population);

		// while evaluations are left
		while (evaluator.hasNext()) {

			// create offspring population until size two times
			SolutionSet<NSGAIISolution<V>> offsprings = new SolutionSet<>(populationSize);
			while (offsprings.size() < populationSize) {

				// select two individuals using the binary tournament selection
				SolutionSet<NSGAIISolution<V>> selected = selector.next(population, 2, rand);

				// crossover of the individuals
				List<V> off = crossover.crossover(selected.get(0).getVariable(), selected.get(1).getVariable(), rand);

				// for every offspring - maybe mutation and add to next
				// generation
				for (V offspring : off) {

					// mutation of the offspring
					if (rand.nextDouble() < this.probMutation) {
						mutation.mutate(offspring, rand);
					}

					// evaluate
					ISolution<V> s = evaluator.evaluate(problem, offspring);

					offsprings.add(new NSGAIISolution<>(s));
				}

			}

			// merge population and offsprings
			population.addAll(offsprings);

			// eliminate duplicates to ensure variety in the population
			if (elimanateDuplicates)
				population = new SolutionSet<>(new HashSet<>(population));

			// survival of the best population
			population = calcRankAndCrowding(population);

			// truncate the population
			population = new SolutionSet<>(population.subList(0, Math.min(populationSize, population.size())));

			// update notifier and evaluator
			if (listener != null) listener.notify(population);
			evaluator.notify(population);

		}

		// System.out.println(evaluator.numOfEvaluations());
		// System.out.println(Hypervolume.calculate(population,Arrays.asList(1.01,
		// 1.01)));

		return new NonDominatedSet<ISolution<V>>(population);

	}

	protected SolutionSet<NSGAIISolution<V>> calcRankAndCrowding(SolutionSet<NSGAIISolution<V>> population) {

		SolutionSet<NSGAIISolution<V>> solutions = new SolutionSet<>();

		// for every front of the population
		int ranking = 0;

		// for every front in the current population
		for (NonDominatedSet<NSGAIISolution<V>> front : SortingBestOrder.sort(population)) {

			SolutionSet<NSGAIISolution<V>> next = front.getSolutions();

			// calculate crowding
			CrowdingDistance.calculate(next);

			// sort by crowding
			Collections.sort(next, (o1, o2) -> Double.compare(o2.getCrowding(), o1.getCrowding()));

			// set the rank as attribute for the binary tournament
			for (NSGAIISolution<V> solution : next)
				solution.setRank(ranking);

			// add the solutions sorted
			solutions.addAll(next);

			// since all other individuals are truncated anyway we could stop
			if (solutions.size() > populationSize)
				break;

			ranking++;

		}

		return solutions;

	}

}
