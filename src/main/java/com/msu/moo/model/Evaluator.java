package com.msu.moo.model;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
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

	// ! the problem which should be solved
	protected IProblem problem;

	// ! current amount of evaluations
	protected int evaluations = 0;

	// ! current amount of evaluations
	protected Integer maxEvaluations = null;

	public Evaluator(IProblem problem) {
		this.problem = problem;
	}

	public Evaluator(IProblem problem, int maxEvaluations) {
		this.problem = problem;
		this.maxEvaluations = maxEvaluations;
	}

	public Solution evaluate(IVariable variable) {
		++evaluations;
		return problem.evaluate(variable);
	}

	public IProblem getProblem() {
		return problem;
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
