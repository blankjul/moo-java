package com.msu.moo.model;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface 
 *
 * @param <V>
 * @param <P>
 */
public abstract class AbstractAlgorithm implements IAlgorithm {

	public abstract NonDominatedSolutionSet run_(IEvaluator evaluator);
	
	// ! name for this algorithm
	protected String name = getClass().getSimpleName();

	
	@Override
	public NonDominatedSolutionSet run(IProblem problem) {
		return run(new Evaluator(problem));
	}
	
	
	@Override
	final public NonDominatedSolutionSet run(IEvaluator evaluator) {
		return run_(evaluator).removeSolutionWithConstraintViolations();
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	


}
