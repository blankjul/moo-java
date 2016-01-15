package com.msu.model;


import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.util.exceptions.EvaluationException;

/**
 * The Evaluator class should be used for each algorithm to evaluate the result
 * of an object of type IVariable.
 * 
 * It is not forbidden to use the problem itself directly, but the evaluator is
 * counting the evaluations and also some other feature like hashing results
 * might be implemented.
 *
 */
public class Evaluator<V extends IVariable, P extends IProblem<V>> implements IEvaluator<V,P> {

	// ! current amount of evaluations
	protected int evaluations = 0;

	// ! current amount of evaluations
	protected Integer maxEvaluations = null;
	
	// ! current amount of evaluations
	protected Evaluator<V,P> father = null;

	
	public Evaluator(int maxEvaluations) {
		this.maxEvaluations = maxEvaluations;
	}

	public Solution<V> evaluate(P problem, V variable) {
		
		if (evaluations >= (int) (maxEvaluations * 1.20)) 
			throw new EvaluationException("Evaluations expired. Check hasNext() first.");
		
		++evaluations;
		if (father != null) father.evaluations++;
		return problem.evaluate(variable);
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

	
	public Evaluator<V,P> createChildEvaluator(int maxEvaluations) {
		Evaluator<V,P> eval = new Evaluator<V,P>(maxEvaluations);
		eval.father = this;
		return eval;
	}
	
	public void increase() {
		++this.evaluations;
		if (getFather() != null) getFather().evaluations++;
	}

	public Evaluator<V,P> getFather() {
		return father;
	}

	@Override
	public Integer numOfEvaluations() {
		return evaluations;
	}
	
	
	

}
