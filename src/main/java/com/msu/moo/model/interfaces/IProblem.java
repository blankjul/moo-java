package com.msu.moo.model.interfaces;

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
public interface IProblem<V extends IVariable> {

	/**
	 * Returns the result of the problem according to the variable
	 * @param variable extends IVariable and could be problem specific.
	 * @return Solution object which contains the result.
	 */
	public Solution evaluate(IVariable variable);

	/**
	 * @return number of evaluations so far.
	 */
	public long getNumOfEvaluations();
	
	
	/**
	 * Reset all evaluations, hashes and so on!
	 */
	public void reset();
	
	
	/**
	 * @return number of objectives that should be optimized!
	 */
	public int getNumberOfObjectives();
	

}
