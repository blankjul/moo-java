package com.msu.model;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface 
 *
 * @param <V>
 * @param <P>
 */
public abstract class AbstractAlgorithm implements IAlgorithm {

	public abstract NonDominatedSolutionSet run_(IEvaluator evaluator, Random rand);
	
	// ! name for this algorithm
	protected String name = getClass().getSimpleName();

	
	final public NonDominatedSolutionSet run(IEvaluator evaluator) {
		return run(evaluator, new Random());
	}
	
	@Override
	final public NonDominatedSolutionSet run(IEvaluator evaluator, Random rand) {
		return run_(evaluator, rand).removeSolutionWithConstraintViolations();
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
