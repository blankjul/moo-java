package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.util.MyRandom;

public abstract class AMultiObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements IMultiObjectiveAlgorithm<V,P>  {

	protected String name;
	
	public abstract NonDominatedSet<ISolution<V>, V> run_(P problem, IEvaluator evaluator, MyRandom rand);

	
	/**
	 * Remove all solutions which are violating the constraints from the non
	 * dominated solution set after executing the implementation of the
	 * multi-objective algorithm.
	 */
	@Override
	public NonDominatedSet<ISolution<V>, V> run(P problem, IEvaluator evaluator, MyRandom rand) {
		return run_(problem, evaluator, rand).removeSolutionWithConstraintViolations();
	}


	
	public AMultiObjectiveAlgorithm() {
		super();
		name = getClass().getSimpleName();
	}



	@Override
	public String toString() {
		return name;
	}




	

	

}
