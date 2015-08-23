package com.msu.moo.model.interfaces;

import com.msu.moo.model.AbstractEvaluator;

/**
 * This interface defines the values of a problem.
 * 
 * Each problem needs to have a evaluation method that has a predefined input I
 * and output o
 * 
 * @param <T>
 *            variable as input
 */
public interface IProblem<V extends IVariable, P extends IProblem<V, P>> {

	/**
	 * @return the evaluator which is used to calculate the result for this
	 *         problem class.
	 */
	public AbstractEvaluator<V,P> getEvaluator();
	

}
