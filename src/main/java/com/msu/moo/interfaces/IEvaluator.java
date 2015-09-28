package com.msu.moo.interfaces;

import com.msu.moo.model.solution.Solution;

public interface IEvaluator<P extends IProblem> {
	
	/**
	 * Evaluate the variable using the problem
	 * @return always MultiObjectiveSolution but could also have one objective
	 */
	public Solution evaluate(IVariable variable);

	/**
	 * @return the problem instance itself
	 */
	public P getProblem();

	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext();

}
