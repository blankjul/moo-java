package com.msu.moo.indicator;

import java.util.Map;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

/**
 * This provides a standardized interface for indicator that
 * could return in principle any type.
 *
 * @param <T> type of the result of indicator
 */
public interface Indicator<T> {
	
	Map<Solution, T> calculate(SolutionSet solutions);

}
