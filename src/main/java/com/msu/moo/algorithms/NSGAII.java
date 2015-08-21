package com.msu.moo.algorithms;

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
import com.msu.moo.util.comparator.RankAndCrowdingComparator;

public class NSGAII<V extends IVariable<?>, P extends IProblem<V, P>> extends Algorithm<V, P> {

	// ! size of the whole Population
	protected int populationSize = 100;
	
	protected double probMutation = 0.2;
	
	protected AbstractCrossover<IVariable<?>> crossover;
	
	protected AbstractMutation<IVariable<?>> mutation;
	
	

	public NSGAII(IFactory<V> factory, long maxEvaluations) {
		super(factory, maxEvaluations);
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

			// create offspring population using binary tournament selection
			SolutionSet Q = new SolutionSet(populationSize);
			RankAndCrowdingComparator cmp = new RankAndCrowdingComparator(rank, crowding);
			BinaryTournamentSelection bts = new BinaryTournamentSelection(P, cmp);
			for (int i = 0; i < populationSize; i++) {
				//List<Solution> offsprings = crossover.crossover(bts.next(), bts.next());
				
			}

		}

		// Create the offspring population with crossover and mutation

		// Calculate the fronts

		// choose all except for last calculate Crowding

		// fill up

		return null;
	}

}
