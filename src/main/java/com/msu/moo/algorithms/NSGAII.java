package com.msu.moo.algorithms;

import java.util.List;
import java.util.Map;

import com.msu.moo.indicator.CrowdingIndicator;
import com.msu.moo.indicator.NonDominatedRankIndicator;
import com.msu.moo.model.Algorithm;
import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.operators.selection.BinaryTournamentSelection;
import com.msu.moo.util.Random;
import com.msu.moo.util.comparator.RankAndCrowdingComparator;

public class NSGAII<V extends IVariable, P extends IProblem<V, P>> extends Algorithm<V, P> {

	// ! size of the whole Population
	protected int populationSize = 100;

	protected double probMutation = 0.2;

	protected AbstractCrossover<?> crossover;

	protected AbstractMutation<?> mutation;

	public NSGAII(IFactory<V> factory, long maxEvaluations, AbstractCrossover<?> crossover,
			AbstractMutation<?> mutation) {
		super(factory, maxEvaluations);
		this.mutation = mutation;
		this.crossover = crossover;
	}

	@Override
	public NonDominatedSolutionSet run(P problem) {

		// initialize the population with populationSize
		SolutionSet P = new SolutionSet(populationSize);
		for (int i = 0; i < populationSize; i++) {
			P.add(problem.getEvaluator().run(factory.create()));
		}

		Map<Solution, Integer> rank = new NonDominatedRankIndicator().calculate(P);
		Map<Solution, Double> crowding = new CrowdingIndicator().calculate(P);

		// if stopping condition false -> evaluations left -> start next
		// generation
		while (maxEvaluations > problem.getEvaluator().count()) {

			// tournament selection
			BinaryTournamentSelection bts = new BinaryTournamentSelection(P,
					new RankAndCrowdingComparator(rank, crowding));

			// create offspring population until size two times
			while (P.size() <= populationSize * 2) {

				// crossover
				List<Solution> offsprings = crossover.crossover(problem.getEvaluator(), bts.next(), bts.next());

				// mutation
				for (Solution offspring : offsprings) {
					if (Random.getInstance().nextDouble() < 0.5)
						offspring = mutation.mutate(problem.getEvaluator(), offspring);
					P.add(offspring);
				}

			}

			rank = new NonDominatedRankIndicator().calculate(P);
			crowding = new CrowdingIndicator().calculate(P);

			// survival of the best population
			P.sort(new RankAndCrowdingComparator(rank, crowding));
			P = new SolutionSet(P.subList(0, populationSize));

		}

		return new NonDominatedSolutionSet(P);
	}

}