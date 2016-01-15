package com.msu.model;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IMultiObjectiveAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.MyRandom;

public abstract class AMultiObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements IMultiObjectiveAlgorithm<V,P>  {

	
	protected P problem;
	
	protected IEvaluator<V, P> evaluator;
	
	protected MyRandom rand;
	
	@Override
	public IMultiObjectiveAlgorithm<V, P> initialize(P problem, IEvaluator<V, P> evaluator, MyRandom rand) {
		this.problem = problem;
		this.evaluator = evaluator;
		this.rand = rand;
		return this;
	}

	
	
	public abstract NonDominatedSolutionSet<V> run_();

	
	/**
	 * Remove all solutions which are violating the constraints from the non
	 * dominated solution set after executing the implementation of the
	 * multi-objective algorithm.
	 */
	@Override
	public NonDominatedSolutionSet<V> run() {
		return run_().removeSolutionWithConstraintViolations();
	}


	@Override
	public String toString() {
		return getClass().getSimpleName();
	}




	

	

}
