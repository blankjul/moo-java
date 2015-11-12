package com.msu.model;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

/**
 * The Evaluator class should be used for each algorithm to evaluate the result
 * of an object of type IVariable.
 * 
 * It is not forbidden to use the problem itself directly, but the evaluator is
 * counting the evaluations and also some other feature like hashing results
 * might be implemented.
 *
 */
public class Evaluator implements IEvaluator{

	// ! current amount of evaluations
	protected int evaluations = 0;

	// ! current amount of evaluations
	protected Integer maxEvaluations = null;

	
	public Evaluator(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}

	public Solution evaluate(IProblem problem, IVariable variable) {
		++evaluations;
		return problem.evaluate(variable);
	}

	public int getEvaluations() {
		return evaluations;
	}


	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext() {
		if (maxEvaluations == null)
			return true;
		return evaluations <= maxEvaluations;
	}

	public Integer getMaxEvaluations() {
		return maxEvaluations;
	}
	
	

}
