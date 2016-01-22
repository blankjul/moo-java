package com.msu.moo.algorithms.nsgaII;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.AMultiObjectiveEvolutionaryAlgorithm;
import com.msu.moo.algorithms.single.SingleObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.selection.BinaryTournamentSelection;
import com.msu.moo.util.MyRandom;

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
	public NonDominatedSolutionSet<V> run_(P problem, IEvaluator evaluator, MyRandom rand)  {

		// initialize the population and calculate also rank and crowding
		initializePopulation(problem, evaluator, rand);
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(String.format("comparison/zdt1/my%s.out", counter++), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		writer.println("#");
		for (Solution<V> solution : population) {
			if (solution.getObjective(1).isNaN()) {
				System.out.println();
			}
			writer.println(String.format("%s	%s", solution.getObjective(0), solution.getObjective(1)));
		}
		
		
		while (evaluator.hasNext()) {
			
			// binary tournament selection for mating
			BinaryTournamentSelection<V> bts = new BinaryTournamentSelection<V>(population,
					new RankAndCrowdingComparator<V>(rank, crowding), rand);

			// create offspring population until size two times
			SolutionSet<V> offsprings = new SolutionSet<V>(populationSize);
			while (offsprings.size() < populationSize) {
				
				// crossover
				Solution<V> p1 = bts.next();
				Solution<V> p2 = bts.next();
				
				List<V> off = crossover.crossover(p1.getVariable(), p2.getVariable(), rand);
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

			// merge population and offsprings
			population.addAll(offsprings);


			
			// eliminate duplicates to ensure variety in the population
			population = new SolutionSet<V>(new HashSet<>(population));
			
			// survival of the best population
			calcRankAndCrowding(population);
			population.sort(new RankAndCrowdingComparator<V>(rank, crowding));
			Collections.reverse(population);
			population = new SolutionSet<V>(population.subList(0, Math.min(populationSize, population.size())));

			evaluator.notify(population);
			
			writer.println("#");
			for (Solution<V> solution : population) {
				writer.println(String.format("%s	%s", solution.getObjective(0), solution.getObjective(1)));
			}
			
			
			//System.out.println(Hypervolume.calculate(population,Arrays.asList(1.01, 1.01)));
		
		}

		writer.flush();
		writer.close();
		
		NonDominatedSolutionSet<V> result = new NonDominatedSolutionSet<V>();
		result.addAll(population);
		return result;

	}

	protected void initializePopulation(P problem, IEvaluator evaluator, MyRandom rand) {
		// create empty indicator maps
		this.rank = new HashMap<>();
		this.crowding = new HashMap<>();
		// initialize the population with populationSize
		population = new SolutionSet<V>(populationSize * 2);

		for (int i = 0; i < populationSize; i++) {
			population.add(evaluator.evaluate(problem, factory.next(rand)));
		}

		// calculate Rank and Crowding for the initial population
		// it is necessary for the BinaryTournamentSelection
		calcRankAndCrowding(population);

	}

	protected void calcRankAndCrowding(SolutionSet<V> population) {
		NonDominatedRankIndicator rankInd = new NonDominatedRankIndicator();
		rank = rankInd.calculate(population);
		crowding = new HashMap<>();
		Collection<NonDominatedSolutionSet<V>> fronts = new NaiveNonDominatedSorting().run(population);
		for (NonDominatedSolutionSet<V> set : fronts) {
			crowding.putAll(new CrowdingIndicator().calculate(set.getSolutions()));
		}
	}


}
