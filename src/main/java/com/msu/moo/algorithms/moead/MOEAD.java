package com.msu.moo.algorithms.moead;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gs.collections.impl.utility.ListIterate;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.algorithms.EvolutionaryAlgorithms;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.Random;
import com.msu.util.Range;
import com.msu.util.Util;


/**
 * 
 *	MOEA/D with some improvements:
 *  - normalize the values before using decomposition metric
 *  - select only with probability delta from neighborhood
 *  - add maximal bound for improved solutions n_r
 *
 *
 */
public class MOEAD extends EvolutionaryAlgorithms {

	//! defines the neighborhood of each weight
	protected int T;
	
	//! probability to select a solution from the neighborhood
	protected double delta = 1.0;
	
	//! maximal number of solutions that could be replaced
	protected int n_r;
	
	protected MOEAD() {
	}

	@Override
	public NonDominatedSolutionSet run_(IProblem problem, IEvaluator eval, Random rand) {

		
		// archive for storing the non dominated front
		NonDominatedSolutionSet archive = new NonDominatedSolutionSet();
		Range<Double> range = new Range<Double>();
		List<Double> fitness = new ArrayList<>();
		

		// initialize the pool of individuals
		population = new SolutionSet(populationSize);
		for (IVariable variable : factory.next(problem, rand, populationSize)) {
			Solution s = eval.evaluate(problem, variable);
			population.add(s);
			range.add(s.getObjective());
		}

		// initialize weights and calculated utility structures
		List<List<Double>> weights = MOEADUtil.getUniformDistributedWeights(rand, populationSize,
				problem.getNumberOfObjectives());

		// calculate the distances between the weights and save T nearest to
		// each weight
		List<List<Double>> mDistance = MOEADUtil.calcDistanceMatrix(weights);
		Map<Integer, List<Integer>> nearestWeights = new HashMap<>();
		for (int i = 0; i < weights.size(); i++) {
			nearestWeights.put(i, MOEADUtil.getNearestTWeights(i, mDistance, T));
		}

		// create reference point which is when the population is normalized
		// [0,0,..0]
		List<Double> z = Util.createListWithDefault(problem.getNumberOfObjectives(), 0d);

		// calculate all fitness values of the population
		
		
		for (int i = 0; i < populationSize; i++) {
			double fitnessValue = MOEADUtil.calcTchebichew(population.get(i).normalize(range.get()).getObjective(),
					weights.get(i), z);
			fitness.add(fitnessValue);
		}

		while (eval.hasNext()) {

			for (int i = 0; i < populationSize; i++) {
				
				// all nearest weights which are updated
				List<Integer> nearest = nearestWeights.get(i);
				

				Solution p1 = null;
				Solution p2 = null;
				// create the child from nearest weights selected individual
				if (rand.nextDouble() < delta) {
					p1 = population.get(rand.select(nearest));
					p2 = population.get(rand.select(nearest));
				// create offspring from the whole population
				} else {
					p1 = rand.select(population);
					p2 = rand.select(population);
				}
				
				List<IVariable> offsprings = crossover.crossover(p1.getVariable(), p2.getVariable(), rand);

				// for each offspring produced by crossover
				for (IVariable off : offsprings) {

					if (rand.nextDouble() < this.probMutation) {
						off = mutation.mutate(off, rand);
					}

					// evaluate and update normalization range
					Solution s = eval.evaluate(problem, off);
					
					// normalize if feasible and new range
					boolean hasToBeNormalized = false;
					if (!s.hasConstrainViolations()) hasToBeNormalized = range.add(s.getObjective());
					
					// normalize the population according to the new range
					if (hasToBeNormalized) {
						for (int k = 0; k < populationSize; k++) {
							fitness.set(k, MOEADUtil.calcTchebichew(population.get(k).normalize(range.get()).getObjective(), weights.get(k),z));
						}
					}
					
					// always randomize order of neighborhood -> n_r is applied
					rand.shuffle(nearest);
					int numOfImproved = 0;

					// for each weight neighbor index
					for (int j : nearest) {
						
						//! if number of improved expired -> break
						if (numOfImproved > n_r) break;
						
						Solution neighbor = population.get(j);

						// if the new solution violates the constraints less
						if (s.getSumOfConstraintViolation() < neighbor.getSumOfConstraintViolation()) {
							population.set(j, s);
							numOfImproved++;
							// equal constraint violation
						} else if (s.getSumOfConstraintViolation().equals(neighbor.getSumOfConstraintViolation())) {

							// calculate single objective of s according to
							// weights
							// of j
							double solutionFitness = MOEADUtil.calcTchebichew(s.normalize(range.get()).getObjective(),
									weights.get(j), z);

							// if new solution is better
							if (solutionFitness < fitness.get(j)) {
								population.set(j, s);
								fitness.set(j, solutionFitness);
								numOfImproved++;
							}
							
						}
					}
				}
			}

		}

		
		// create the final archive
		ListIterate.forEach(population, p -> archive.add(p));

		return archive;
	}

}
