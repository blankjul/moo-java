package com.msu.model;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.Random;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface
 *
 * @param <V>
 * @param
 * 			<P>
 */
public abstract class AbstractDomainAlgorithm<P extends IProblem> extends AbstractAlgorithm {

	public abstract NonDominatedSolutionSet run__(P problem, IEvaluator evaluator, Random rand);

	@Override
	final public NonDominatedSolutionSet run_(IProblem problem, IEvaluator evaluator, Random rand) {
		try {
			@SuppressWarnings("unchecked")
			P domainProblem = (P) problem;
			return run__(domainProblem, evaluator, rand);
		} catch (Exception e) {
			throw new RuntimeException("AbstractDomainAlgorithm is not used for the defined problem!");
		}
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
