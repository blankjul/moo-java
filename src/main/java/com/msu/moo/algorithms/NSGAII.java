package com.msu.moo.algorithms;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.model.AbstractAlgorithm;
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

public class NSGAII<V extends IVariable, P extends IProblem> extends AbstractAlgorithm<V, P> {

	// ! size of the whole Population
	protected int populationSize = 100;

	// ! default mutation probability
	protected double probMutation = 0.3;

	// ! operator for crossover
	protected AbstractCrossover<?> crossover;

	// ! operator for mutation
	protected AbstractMutation<?> mutation;

	// ! current population
	protected SolutionSet population = null;

	// ! rank for the whole population
	protected Map<Solution, Integer> rank;

	// ! crowding distance for the whole population
	protected Map<Solution, Double> crowding;

	// ! factory for creating new instances
	protected IVariableFactory<V, P> factory;

	public NSGAII(IVariableFactory<V, P> factory, AbstractCrossover<?> crossover, AbstractMutation<?> mutation) {
		this.factory = factory;
		this.mutation = mutation;
		this.crossover = crossover;
	}

	protected void initialize(P problem) {
		// create empty indicator maps
		this.rank = new HashMap<>();
		this.crowding = new HashMap<>();
		// initialize the population with populationSize
		population = new SolutionSet(populationSize * 2);
		for (int i = 0; i < populationSize; i++) {
			population.add(problem.evaluate(factory.next(problem)));
		}

		// calculate Rank and Crowding for the initial population
		// it is necessary for the BinaryTournamentSelection
		calcRankAndCrowding(population);

	}

	@Override
	public NonDominatedSolutionSet run(P problem, long maxEvaluations) {
		initialize(problem);

		while (problem.getNumOfEvaluations() < maxEvaluations) {

			// binary tournament selection for mating
			BinaryTournamentSelection bts = new BinaryTournamentSelection(population, new RankAndCrowdingComparator(rank, crowding));

			// create offspring population until size two times
			SolutionSet offsprings = new SolutionSet(populationSize);
			while (offsprings.size() < populationSize) {
				// crossover
				List<IVariable> off = crossover.crossover(bts.next().getVariable(), bts.next().getVariable());
				// mutation
				for (IVariable offspring : off) {
					if (Random.getInstance().nextDouble() < this.probMutation)
						offspring = mutation.mutate(offspring);
					offsprings.add(problem.evaluate(offspring));
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
