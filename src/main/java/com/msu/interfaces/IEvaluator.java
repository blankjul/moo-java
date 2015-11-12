package com.msu.interfaces;

import com.msu.moo.model.solution.Solution;

public interface IEvaluator {

	/**
	 * Evaluate the variable using the problem
	 * 
	 * @return always MultiObjectiveSolution but could also have one objective
	 */
	public Solution evaluate(IProblem problem, IVariable variable);


	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext();

	/**
	 * Each Evaluator should have a maximal number of available evaluations.
	 * 
	 * @return maximal allowed evaluations
	 */
	public Integer getMaxEvaluations();

}
