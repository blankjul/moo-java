package com.msu.moo.algorithms.nsgaII;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.algorithms.AMultiObjectiveEvolutionaryAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.util.MyRandom;

/**
 * This algorithm is implemented in the base of NSGAII proposed by Professor Deb
 * in "A fast and elitist multiobjective genetic algorithm: NSGA-II".
 * 
 *
 */
public class NSGAII<V extends IVariable, P extends IProblem<V>> extends AMultiObjectiveEvolutionaryAlgorithm<V,P> {

	// ! rank for the whole population
	protected Map<Solution<V>, Integer> rank;

	// ! crowding distance for the whole population
	protected Map<Solution<V>, Double> crowding;

	// ! private constructor! use the builder!
	protected NSGAII() {
	}

	@Override
	public NonDominatedSolutionSet<V> run_(P problem, IEvaluator<V,P> evaluator,
			MyRandom rand) {

		// initialize the population and calculate also rank and crowding
		initializePopulation(problem, evaluator, rand);
		
		
		while (evaluator.hasNext()) {

			// binary tournament selection for mating
			BinaryTournamentSelection<V> bts = new BinaryTournamentSelection<V>(population,
					new RankAndCrowdingComparator<V>(rank, crowding), rand);

			// create offspring population until size two times
			SolutionSet<V> offsprings = new SolutionSet<V>(populationSize);
			while (offsprings.size() < populationSize) {
				// crossover
				List<V> off = crossover.crossover(problem, rand, bts.next().getVariable(), bts.next().getVariable());
				// mutation
				for (V offspring : off) {
					if (rand.nextDouble() < this.probMutation) {
						mutation.mutate(problem, rand, offspring);
					}
					offsprings.add(evaluator.evaluate(problem, offspring));
				}
			}

			// merge population and offsprings
			population.addAll(offsprings);


			
			// eliminate duplicates to ensure variety in the population
			population = new SolutionSet<V>(new HashSet<>(population));
			
			// survival of the best population
			calcRankAndCrowding(population);
			population.sort(new RankAndCrowdingComparator<V>(rank, crowding));
			Collections.reverse(population);
			population = new SolutionSet<V>(population.subList(0, Math.min(populationSize, population.size())));

		}

		NonDominatedSolutionSet<V> result = new NonDominatedSolutionSet<V>();
		result.setSolutionDominator(new SolutionDominatorWithConstraints());
		result.addAll(population);
		return result;

	}

	protected void initializePopulation(P problem, IEvaluator<V,P> evaluator, MyRandom rand) {
		// create empty indicator maps
		this.rank = new HashMap<>();
		this.crowding = new HashMap<>();
		// initialize the population with populationSize
		population = new SolutionSet<V>(populationSize * 2);

		for (int i = 0; i < populationSize; i++) {
			population.add(evaluator.evaluate(problem, factory.next(problem, rand)));
		}

		// calculate Rank and Crowding for the initial population
		// it is necessary for the BinaryTournamentSelection
		calcRankAndCrowding(population);

	}

	protected void calcRankAndCrowding(SolutionSet<V> population) {
		NonDominatedRankIndicator rankInd = new NonDominatedRankIndicator();
		rank = rankInd.calculate(population);
		crowding = new HashMap<>();
		Collection<NonDominatedSolutionSet<V>> fronts = new NaiveNonDominatedSorting(new SolutionDominatorWithConstraints()).run(population);
		for (NonDominatedSolutionSet<V> set : fronts) {
			crowding.putAll(new CrowdingIndicator().calculate(set.getSolutions()));
		}
	}


}
