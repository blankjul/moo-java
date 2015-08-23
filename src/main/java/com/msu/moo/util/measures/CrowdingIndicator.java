package com.msu.moo.util.measures;

import java.util.HashMap;
import java.util.Map;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class CrowdingIndicator implements Measure<Double>{

	
	//! helping method for fast access to objective values
	private Double get(SolutionSet solutions, int index, int objective) {
		return solutions.get(index).getObjectives().get(objective);
	}
	
	@Override
	public Map<Solution, Double> calculate(SolutionSet solutions) {
		
		// the result
		Map<Solution, Double> map = new HashMap<>();
		for (Solution s : solutions) {
			map.put(s, 0d);
		}
		
		// the resulting set is also empty
		if (solutions.isEmpty()) return map;
		
		// the resulting set is also empty
		if (solutions.size() <= 2) {
			for (Solution s : solutions) map.put(s, Double.POSITIVE_INFINITY);
		}
		
		// create a copy of the input because they must be sorted 
		SolutionSet copy = new SolutionSet(solutions);
		
		// for all objectives
		int numberOfObjectives = copy.get(0).countObjectives();
		for (int i = 0; i < numberOfObjectives; i++) {
			copy.sortByObjective(i);
			
			// calculate minimal and maximal which defines the denominator
			double min = get(copy, 0, i); 
			double max = get(copy, copy.size()-1, i);
			double denominator = max - min;
			
			// the boundaries elements are always maximal
			map.put(copy.get(0), Double.POSITIVE_INFINITY);
			map.put(copy.get(copy.size()- 1), Double.POSITIVE_INFINITY);
			
			// calculate the value for all elements inside
			for (int j = 1; j < copy.size()- 1; j++) {
				double value = (get(copy, j+1, i) - get(copy, j-1, i)) / denominator; 
				map.put(copy.get(j), map.get(copy.get(j)) + value);
			}
			
		}
		
		
		return map;
	}

}
