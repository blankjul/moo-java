package com.msu.moo.algorithms.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.model.Algorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.operators.selection.BinaryTournamentSelection;
import com.msu.moo.util.Random;
import com.msu.moo.util.comparator.RankAndCrowdingComparator;
import com.msu.moo.util.indicator.CrowdingIndicator;
import com.msu.moo.util.indicator.NonDominatedRankIndicator;

/**
 * This algorithm is implemented in the base of NSGAII proposed by Professor
 * Deb in "A fast and elitist multiobjective genetic algorithm: NSGA-II".
 * 
 *
 */
public class NSGAII<V extends IVariable, P extends IProblem> extends Algorithm<V, P> {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected double probMutation;

	// ! operator for crossover
	protected AbstractCrossover<?> crossover;

	// ! operator for mutation
	protected AbstractMutation<?> mutation;

	// ! rank for the whole population
	protected Map<Solution, Integer> rank;

	// ! crowding distance for the whole population
	protected Map<Solution, Double> crowding;

	// ! factory for creating new instances
	protected IVariableFactory<V, P> factory;

	// ! current population
	protected SolutionSet population = null;

	//! private constructor! use the builder!
	protected NSGAII() {}

	@Override
	public NonDominatedSolutionSet run(Evaluator<P> evaluator) {

		// initialize the population and calculate also rank and crowding
		initialize(evaluator);

		while (evaluator.hasNext()) {

			// binary tournament selection for mating
			BinaryTournamentSelection bts = new BinaryTournamentSelection(population, 
					new RankAndCrowdingComparator(rank, crowding));

			// create offspring population until size two times
			SolutionSet offsprings = new SolutionSet(populationSize);
			while (offsprings.size() < populationSize) {
				// crossover
				List<IVariable> off = crossover.crossover(bts.next().getVariable(), bts.next().getVariable());
				// mutation
				for (IVariable offspring : off) {
					if (Random.getInstance().nextDouble() < this.probMutation)
						offspring = mutation.mutate(offspring);
					offsprings.add(evaluator.evaluate(offspring));
				}
			}

			// merge population and offsprings
			population.addAll(offsprings);

			// survival of the best population
			calcRankAndCrowding(population);
			population.sort(new RankAndCrowdingComparator(rank, crowding));
			Collections.reverse(population);

			population = new SolutionSet(population.subList(0, populationSize));
		}
		return new NonDominatedSolutionSet(population);
	}

	protected void initialize(Evaluator<P> evaluator) {
		// create empty indicator maps
		this.rank = new HashMap<>();
		this.crowding = new HashMap<>();
		// initialize the population with populationSize
		population = new SolutionSet(populationSize * 2);
		for (int i = 0; i < populationSize; i++) {
			population.add(evaluator.evaluate(factory.next(evaluator.getProblem())));
		}

		// calculate Rank and Crowding for the initial population
		// it is necessary for the BinaryTournamentSelection
		calcRankAndCrowding(population);

	}

	protected void calcRankAndCrowding(SolutionSet population) {
		NonDominatedRankIndicator rankInd = new NonDominatedRankIndicator();
		rank = rankInd.calculate(population);
		crowding = new HashMap<>();
		for (NonDominatedSolutionSet set : rankInd.getNonDominatedSets()) {
			CrowdingIndicator crowdInd = new CrowdingIndicator();
			crowdInd.calculate(crowding, set.getSolutions());
		}
	}

}
