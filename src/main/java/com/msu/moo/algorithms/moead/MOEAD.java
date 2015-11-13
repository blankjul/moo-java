package com.msu.moo.algorithms.moead;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.algorithms.EvolutionaryAlgorithms;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.Random;
import com.msu.util.Range;

public class MOEAD extends EvolutionaryAlgorithms {

	// defines the neighborhood of each weight
	protected int T;

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
		Double[] tmp = new Double[problem.getNumberOfObjectives()];
		Arrays.fill(tmp, 0d);
		List<Double> z = Arrays.asList(tmp);

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

				// create the child from nearest weights selected individual
				Solution p1 = population.get(rand.select(nearest));
				Solution p2 = population.get(rand.select(nearest));
				List<IVariable> offsprings = crossover.crossover(p1.getVariable(), p2.getVariable(), rand);

				// for each offspring produced by crossover
				for (IVariable off : offsprings) {

					if (rand.nextDouble() < this.probMutation) {
						off = mutation.mutate(off, rand);
					}

					// evaluate and update normalization range
					Solution s = eval.evaluate(problem, off);
					boolean hasToBeNormalized = range.add(s.getObjective());
					
					// normalize the population according to the new range
					if (hasToBeNormalized) {
						for (int k = 0; k < populationSize; k++) {
							fitness.set(k, MOEADUtil.calcTchebichew(population.get(k).normalize(range.get()).getObjective(), weights.get(k),z));
						}
					}

					// for each weight neighbor index
					for (int j : nearest) {

						Solution neighbor = population.get(j);

						// if the new solution violates the constraints less
						if (s.getSumOfConstraintViolation() < neighbor.getSumOfConstraintViolation()) {
							population.set(j, s);

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
							}
							
						}

					}
				}
			}

		}

		// create non dominated set from population
		for (int k = 0; k < populationSize; k++) {
			archive.add(population.get(k));
		}

		return archive;
	}

}
