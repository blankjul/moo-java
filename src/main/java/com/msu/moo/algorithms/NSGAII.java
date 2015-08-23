package com.msu.moo.algorithms;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.AbstractEvaluator;
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
import com.msu.moo.util.measures.CrowdingIndicator;
import com.msu.moo.util.measures.NonDominatedRankIndicator;

public class NSGAII<V extends IVariable, P extends IProblem<V, P>> extends AbstractAlgorithm<V, P> {

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
		
		AbstractEvaluator<V, P> eval = problem.getEvaluator();

		// initialize the population with populationSize
		SolutionSet P = new SolutionSet(populationSize * 2);
		for (int i = 0; i < populationSize; i++) {
			P.add(problem.getEvaluator().run(factory.create()));
		}

		Map<Solution, Integer> rank = new NonDominatedRankIndicator().calculate(P);
		Map<Solution, Double> crowding = new CrowdingIndicator().calculate(P);

		// if stopping condition false -> evaluations left -> start next
		// generation
		while (maxEvaluations > eval.count()) {

			// tournament selection
			BinaryTournamentSelection bts = new BinaryTournamentSelection(P,
					new RankAndCrowdingComparator(rank, crowding));

			SolutionSet Q = new SolutionSet(populationSize);
			
			// create offspring population until size two times
			while (Q.size() < populationSize) {

				// crossover
				List<IVariable> offsprings = crossover.crossover(bts.next().getVariable(), bts.next().getVariable());

				// mutation
				for (IVariable offspring : offsprings) {
					if (Random.getInstance().nextDouble() < 0.5)
						offspring = mutation.mutate(offspring);
					Q.add(eval.run(offspring));
				}

			}
			
			// merge parents and offsprings
			P.addAll(Q);

			rank = new NonDominatedRankIndicator().calculate(P);
			crowding = new CrowdingIndicator().calculate(P);

			// survival of the best population
			P.sort(new RankAndCrowdingComparator(rank, crowding));
			Collections.reverse(P);
			
			/*
			for (Solution s : P) {
				System.out.println(String.format("rank%s | crowding:%s", rank.get(s), crowding.get(s)));
			}
			System.out.println("----------------");
			*/
			
			P = new SolutionSet(P.subList(0, populationSize));

		}

		return new NonDominatedSolutionSet(P);
	}

}
