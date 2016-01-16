package com.msu.moo.model;

import java.util.Map;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

/**
 * This provides a standardized class for indicator that could return in
 * principle any type.
 *
 * @param <T>
 *            type of the result of indicator
 */
public abstract class AIndicator<E> {

	/**
	 * Calculate indicator values and return a map with with result.
	 */
	public abstract <T> Map<Solution<T>, E> calculate(SolutionSet<T> solutions);

}