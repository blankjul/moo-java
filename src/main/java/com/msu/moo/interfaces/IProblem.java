package com.msu.moo.interfaces;

import com.msu.moo.model.solution.Solution;

/**
 * This interface defines the values of a problem.
 * 
 * Each problem needs to have a evaluation method that has a predefined input I
 * and output o
 * 
 * @param <T>
 *            variable as input
 */
public interface IProblem {

	/**
	 * Returns the result of the problem according to the variable
	 * @param variable extends IVariable and could be problem specific.
	 * @return Solution object which contains the result.
	 */
	public Solution evaluate(IVariable variable);


	/**
	 * @return number of objectives that should be optimized!
	 */
	public int getNumberOfObjectives();
	

}
