package com.msu.model;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.MyRandom;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface 
 *
 * @param <V>
 * @param <P>
 */
public abstract class AbstractAlgorithm implements IAlgorithm {

	
	public abstract NonDominatedSolutionSet run_(IProblem problem, IEvaluator evaluator, MyRandom rand);
	
	@Override
	final public NonDominatedSolutionSet run(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		return run_(problem, evaluator, rand).removeSolutionWithConstraintViolations();
	}
	
	// ! name for this algorithm
	protected String name = getClass().getSimpleName();


	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	


}
