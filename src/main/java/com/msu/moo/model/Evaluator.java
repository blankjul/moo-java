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

	public Evaluator(P problem) {
		super();
		this.problem = problem;
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

}
