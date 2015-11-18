package com.msu.moo.algorithms.nsgaII;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.algorithms.EvolutionaryAlgorithms;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.util.Random;

/**
 * This algorithm is implemented in the base of NSGAII proposed by Professor
 * Deb in "A fast and elitist multiobjective genetic algorithm: NSGA-II".
 * 
 *
 */
public class NSGAII extends EvolutionaryAlgorithms {

	// ! rank for the whole population
	protected Map<Solution, Integer> rank;

	// ! crowding distance for the whole population
	protected Map<Solution, Double> crowding;

	//! function that allows to modify the population after mating
	protected INSGAIIModifactor funcModify = null;

	//! private constructor! use the builder!
	protected NSGAII() {}

	
	@Override
	public NonDominatedSolutionSet run_(IProblem problem, IEvaluator evaluator, Random rand) {

		// initialize the population and calculate also rank and crowding
		initialize(problem, evaluator, rand);
		
		while (evaluator.hasNext()) {
			
			// binary tournament selection for mating
			BinaryTournamentSelection bts = new BinaryTournamentSelection(population, 
					new RankAndCrowdingComparator(rank, crowding), rand);

			// create offspring population until size two times
			SolutionSet offsprings = new SolutionSet(populationSize);
			while (offsprings.size() < populationSize) {
				// crossover
				List<IVariable> off = crossover.crossover(bts.next().getVariable(), bts.next().getVariable(), rand);
				// mutation
				for (IVariable offspring : off) {
					if (rand.nextDouble() < this.probMutation)
						offspring = mutation.mutate(offspring, problem, rand);
					offsprings.add(evaluator.evaluate(problem, offspring));
				}
			}

			// merge population and offsprings
			population.addAll(offsprings);
			
			// modify population with the given function
			if (funcModify != null) {
				funcModify.modify(problem, evaluator, population, rand);
			}
			
			// survival of the best population
			calcRankAndCrowding(population);
			population.sort(new RankAndCrowdingComparator(rank, crowding));
			Collections.reverse(population);

			population = new SolutionSet(population.subList(0, populationSize));
			
			
		}
		
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		result.setSolutionDominator(new SolutionDominatorWithConstraints());
		result.addAll(population);
		return result;
		
	}

	protected void initialize(IProblem problem, IEvaluator eval, Random rand) {
		// create empty indicator maps
		this.rank = new HashMap<>();
		this.crowding = new HashMap<>();
		// initialize the population with populationSize
		population = new SolutionSet(populationSize * 2);
		
		for (IVariable variable : factory.next(problem, rand, populationSize)) {
			population.add(eval.evaluate(problem, variable));
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
