package com.msu.moo.interfaces;

import com.msu.moo.model.solution.Solution;

/**
 * This interface defines a problem which is able to evaluate a solution.
 * 
 * @param <E> encoded type of the variable which is used.
 *         
 */
public interface IProblem<V extends IVariable> {

	/**
	 * Returns the result of the problem according to the variable
	 * @param variable extends IVariable and could be problem specific.
	 * @return Solution object which contains the result.
	 */
	public Solution<V> evaluate(V variable);


	/**
	 * @return number of objectives that should be optimized!
	 */
	public int getNumberOfObjectives();
	
	
	/**
	 * @return number of objectives that should be optimized!
	 */
	public int getNumberOfConstraints();
	


}
