package com.msu.moo.algorithms.nsgaII;

import java.util.HashMap;
import java.util.Map;

import com.msu.model.AIndicator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class CrowdingIndicator extends AIndicator<Double> {

	// ! helping method for fast access to objective values
	private Double get(SolutionSet<?> solutions, int index, int objective) {
		return solutions.get(index).getObjective(objective);
	}

	@Override
	public <T> Map<Solution<T>, Double> calculate(SolutionSet<T> solutions) {
		Map<Solution<T>, Double> map = new HashMap<>();

		// the result
		for (Solution<T> s : solutions) {
			map.put(s, 0d);
		}

		// the resulting set is also empty
		if (solutions.isEmpty())
			return map;

		// the resulting set is also empty
		if (solutions.size() <= 2) {
			for (Solution<T> s : solutions)
				map.put(s, Double.POSITIVE_INFINITY);
		}

		// create a copy of the input because they must be sorted
		SolutionSet<T> copy = new SolutionSet<T>(solutions);

		// for all objectives
		int numberOfObjectives = copy.get(0).numOfObjectives();
		for (int i = 0; i < numberOfObjectives; i++) {
			copy.sortByObjective(i);

			// calculate minimal and maximal which defines the denominator
			double min = get(copy, 0, i);
			double max = get(copy, copy.size() - 1, i);
			double denominator = max - min;

			// the boundaries elements are always maximal
			map.put(copy.get(0), Double.POSITIVE_INFINITY);
			map.put(copy.get(copy.size() - 1), Double.POSITIVE_INFINITY);

			// calculate the value for all elements inside
			for (int j = 1; j < copy.size() - 1; j++) {
				double value = (get(copy, j + 1, i) - get(copy, j - 1, i)) / denominator;
				map.put(copy.get(j), map.get(copy.get(j)) + value);
			}

		}

		return map;
	}

}
