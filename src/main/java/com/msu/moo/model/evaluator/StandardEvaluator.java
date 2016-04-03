package com.msu.moo.model.evaluator;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.exceptions.EvaluationException;

/**
 * The evaluations are expired when the maximal number of evaluations is done
 * using this evaluator.
 *
 */
public class StandardEvaluator extends AEvaluator {

	// ! current amount of evaluations
	protected Integer maxEvaluations = null;

	public StandardEvaluator(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}

	public <V extends IVariable> Solution<V> evaluate(IProblem<? extends IVariable> problem, V variable) {

		// 20 percent are okay - some times the population size will be larger..
		if (getEvaluations() >= (int) (maxEvaluations * 1.20))
			throw new EvaluationException("Evaluations expired. Check hasNext() first.");

		return super.evaluate(problem, variable);

	}

	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext() {
		return getEvaluations() < maxEvaluations;
	}

	public Integer getMaxEvaluations() {
		return maxEvaluations;
	}

}
