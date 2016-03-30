package com.msu.moo.model.evaluator;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.exceptions.EvaluationException;

/**
 * The Evaluator class should be used for each algorithm to evaluate the result
 * of an object of type IVariable.
 * 
 * It is not forbidden to use the problem itself directly, but the evaluator is
 * counting the evaluations and also some other feature like hashing results
 * might be implemented.
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

	@Override
	public <V extends IVariable> void notify(SolutionSet<V> set) {
	}
	
	

}
