package com.msu.moo.model;

import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

public abstract class AMultiObjectiveProblem<V extends IVariable> implements IProblem {

	/**
	 * Evaluation method that must be implemented by all subclasses.
	 * @param variable input value
	 * @return objective results
	 */
	protected abstract List<Double> evaluate_(V variable);
	
	// ! name of this problem instance
	protected String name = this.getClass().getSimpleName();

	@Override
	public Solution evaluate(IVariable variable) {

		@SuppressWarnings("unchecked")
		V v = (V) variable;

		List<Double> objectives = evaluate_(v);
		return new Solution(variable, objectives);
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	


}
