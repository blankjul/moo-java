package com.msu.moo.model;

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
public class Evaluator<P extends IProblem> {

	// ! the problem which should be solved
	protected P problem;

	// ! current amount of evaluations
	protected int evaluations = 0;

	// ! current amount of evaluations
	protected Integer maxEvaluations = null;

	public Evaluator(P problem) {
		this.problem = problem;
	}

	public Evaluator(P problem, int maxEvaluations) {
		this.problem = problem;
		this.maxEvaluations = maxEvaluations;
	}

	public Solution evaluate(IVariable variable) {
		++evaluations;
		return problem.evaluate(variable);
	}

	public P getProblem() {
		return problem;
	}

	public int getEvaluations() {
		return evaluations;
	}

	public Evaluator<P> setEvaluations(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
		return this;
	}

	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext() {
		if (maxEvaluations == null)
			return true;
		return evaluations < maxEvaluations;
	}

}
