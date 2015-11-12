package com.msu.model;

import java.util.HashMap;
import java.util.Map;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

/**
 * This provides a standardized class for indicator that
 * could return in principle any type.
 *
 * @param <T> type of the result of indicator
 */
public abstract class AIndicator<T> {
	
	/**
	 * Calculate indicator values and return a map with with result.
	 */
	public Map<Solution, T> calculate(SolutionSet solutions) {
		Map<Solution, T> m = new HashMap<>();
		calculate(m, solutions);
		return m;
	}

	/**
	 * Add the result to the given map.
	 */
	public abstract void calculate(Map<Solution, T> map, SolutionSet solutions);
 
	
}
