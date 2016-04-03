package com.msu.moo.algorithms.impl.nsgaII;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.msu.moo.model.solution.SolutionSet;

public class CrowdingDistance {

	// ! helping method for fast access to objective values
	private static <S extends NSGAIISolution<V>,V> Double get(List<S> solutions, int index, int objective) {
		return solutions.get(index).getObjective(objective);
	}
	
	public static <S extends NSGAIISolution<V>,V> void calculate(SolutionSet<S> solutions) {
		
		// the resulting set is also empty
		if (solutions.isEmpty()) return;
		
		SolutionSet<S> copy = new SolutionSet<S>(solutions);
		copy.forEach(s -> s.setCrowding(0.0));
		

		// the resulting set is also empty
		if (copy.size() <= 2) {
			for (NSGAIISolution<V> s : copy) s.setCrowding(Double.POSITIVE_INFINITY);
		}

		// for all objectives
		int numberOfObjectives = copy.get(0).numOfObjectives();
		
		for (int i = 0; i < numberOfObjectives; i++) {
			
			// sort according objective i
			final int obj = i;
			Comparator<NSGAIISolution<V>> comp = 
					(NSGAIISolution<V> s1, NSGAIISolution<V> s2)-> s1.getObjective(obj).compareTo(s2.getObjective(obj));
			Collections.sort(copy, comp);

			// calculate minimal and maximal which defines the denominator
			double min = get(copy, 0, i);
			double max = get(copy, copy.size() - 1, i);
			double denominator = max - min;

			// the boundaries elements are always maximal
			copy.get(0).setCrowding(Double.POSITIVE_INFINITY);
			copy.get(copy.size() - 1).setCrowding(Double.POSITIVE_INFINITY);

			// calculate the value for all elements inside
			for (int j = 1; j < copy.size() - 1; j++) {
				double value = (get(copy, j + 1, i) - get(copy, j - 1, i)) / denominator;
				copy.get(j).setCrowding(copy.get(j).getCrowding() + value);
			}

		}

	}

}
