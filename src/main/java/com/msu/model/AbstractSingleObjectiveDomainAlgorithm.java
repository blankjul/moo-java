package com.msu.model;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.Solution;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.util.MyRandom;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface
 *
 * @param <V>
 * @param
 * 			<P>
 */
public abstract class AbstractSingleObjectiveDomainAlgorithm<P extends IProblem> extends ASingleObjectiveAlgorithm {

	public abstract Solution run___(P problem, IEvaluator evaluator, MyRandom rand);

	@SuppressWarnings("unchecked")
	@Override
	public Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		P domainProblem = null;
		try {
			domainProblem = (P) problem;
		} catch (Exception e) {
			throw new RuntimeException("AbstractDomainAlgorithm is not used for the defined problem!");
		}
		return run___(domainProblem, evaluator, rand);
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
