package com.msu.moo.model.evaluator;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

/**
 * Unlimited evaluations. Will never terminate!
 *
 */
public class UnlimitedEvaluator extends AEvaluator {

	
	public <V extends IVariable> Solution<V> evaluate(IProblem<? extends IVariable> problem, V variable) {
		return super.evaluate(problem, variable);
	}

	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext() {
		return true;
	}


	@Override
	public Integer getMaxEvaluations() {
		return Integer.MAX_VALUE;
	}
	
	

}
