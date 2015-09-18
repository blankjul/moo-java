package com.msu.moo.model;

import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.MultiObjectiveSolution;

public abstract class AProblem<V extends IVariable> implements IProblem {

	/**
	 * Evaluation method that must be implemented by all subclasses.
	 * @param variable input value
	 * @return objective results
	 */
	protected abstract List<Double> evaluate_(V variable);
	

	@Override
	public MultiObjectiveSolution evaluate(IVariable variable) {

		@SuppressWarnings("unchecked")
		V v = (V) variable;

		List<Double> objectives = evaluate_(v);
		return new MultiObjectiveSolution(variable, objectives);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}


}
