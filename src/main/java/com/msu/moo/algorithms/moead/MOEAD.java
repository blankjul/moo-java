package com.msu.moo.algorithms.moead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IVariable;
import com.msu.moo.algorithms.EvolutionaryAlgorithms;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Random;

public class MOEAD extends EvolutionaryAlgorithms {

	// defines the neighborhood of each weight
	protected int T;

	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval, Random rand) {

		// archive for storing the non dominated front
		NonDominatedSolutionSet archive = new NonDominatedSolutionSet();

		// initialize the pool of individuals
		population = new SolutionSet(populationSize);
		for (IVariable variable : factory.next(eval.getProblem(), rand, populationSize)) {
			Solution s = eval.evaluate(variable);
			population.add(s);
			archive.add(s);
		}

		// initialize weights and calculated utility structures
		List<List<Double>> weights = MOEADUtil.getUniformDistributedWeights(rand, populationSize);

		
		Collections.sort(weights, new Comparator<List<Double>>() {
			@Override
			public int compare(List<Double> o1, List<Double> o2) {
				return o1.get(0).compareTo(o2.get(0));
			}
		});

		// initialize reference point
		List<Double> z = new ArrayList<>();
		for (int i = 0; i < eval.getProblem().getNumberOfObjectives(); i++) {
			z.add(Double.POSITIVE_INFINITY);
		}

		List<List<Double>> mDistance = MOEADUtil.calcDistanceMatrix(weights);
		Map<Integer, List<Integer>> nearestWeights = new HashMap<>();
		for (int i = 0; i < weights.size(); i++) {
			nearestWeights.put(i, MOEADUtil.getNearestTWeights(i, mDistance, T));
		}

		/*
		 * for (int i = 0; i < populationSize; i++) {
		 * MOEADUtil.updateReferencePoint(z, population.get(i).getObjective());
		 * }
		 */

		List<Double> fitness = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			fitness.add(Double.POSITIVE_INFINITY);
			//fitness.add(MOEADUtil.calcWeightedSum(population.get(i).getObjective(), weights.get(i)));
		}


		while (eval.hasNext()) {

			for (int i = 0; i < populationSize; i++) {

				// all nearest weights. update only individuals contained in
				// that list
				List<Integer> nearest = nearestWeights.get(i);

				// create the child from nearest weights
				Solution p1 = population.get(rand.select(nearest));
				Solution p2 = population.get(rand.select(nearest));
				List<IVariable> offsprings = crossover.crossover(p1.getVariable(), p2.getVariable(), rand);

				for (IVariable off : offsprings) {

					if (rand.nextDouble() < this.probMutation) {
						off = mutation.mutate(off, rand);
					}

					// evaluate variable and check if it improves function
					// values
					Solution s = eval.evaluate(off);
					MOEADUtil.updateReferencePoint(z, s.getObjective());

					for (int j : nearest) {

						// calculate single objective according to weights of j
						double singleObjValue = MOEADUtil.calcWeightedSum(s.getObjective(), weights.get(j));

						// if the new solution is better than the old one
						if (eval.getProblem().getNumberOfConstraints() == 0 || 
								Collections.max(s.getConstraintViolations()) <= Collections.max(population.get(j).getConstraintViolations())) {
							if (singleObjValue < fitness.get(j)) {
								population.set(j, s);
								fitness.set(j, singleObjValue);
							}
						}
					}

					// keep the best solutions in an archive
					archive.add(s);
				}
			}

			//System.out.println(String.format("%s:%s", gen++, counter));

		}

		//System.out.println(Arrays.toString(fitness.toArray()));

		return archive;
	}

}
