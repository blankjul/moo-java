package com.msu.moo.model.evaluator;

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
