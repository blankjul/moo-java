package com.msu.moo.model;

import java.util.List;

import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

public abstract class AbstractProblem<V extends IVariable> implements IProblem {

	/**
	 * Evaluation method that must be implemented by all subclasses.
	 * @param variable input value
	 * @return objective results
	 */
	protected abstract List<Double> evaluate_(V variable);
	
	
	// ! number of evaluations so far
	protected long numOfEvaluations = 0;

	@Override
	public long getNumOfEvaluations() {
		return numOfEvaluations;
	}

	@Override
	public void reset() {
		this.numOfEvaluations = 0;
	}

	@Override
	public Solution evaluate(IVariable variable) {
		// TODO: Hash the result value and return it directly if it fits!

		// increase amount of evaluations
		++numOfEvaluations;

		@SuppressWarnings("unchecked")
		V v = (V) variable;

		List<Double> objectives = evaluate_(v);
		return new Solution(variable, objectives);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}


}
