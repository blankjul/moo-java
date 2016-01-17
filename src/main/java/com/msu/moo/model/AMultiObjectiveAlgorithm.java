package com.msu.moo.model;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IMultiObjectiveAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.MyRandom;

public abstract class AMultiObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements IMultiObjectiveAlgorithm<V,P>  {

	
	public abstract NonDominatedSolutionSet<V> run_(P problem, IEvaluator evaluator, MyRandom rand);

	
	/**
	 * Remove all solutions which are violating the constraints from the non
	 * dominated solution set after executing the implementation of the
	 * multi-objective algorithm.
	 */
	@Override
	public NonDominatedSolutionSet<V> run(P problem, IEvaluator evaluator, MyRandom rand) {
		return run_(problem, evaluator, rand).removeSolutionWithConstraintViolations();
	}


	@Override
	public String toString() {
		return getClass().getSimpleName();
	}




	

	

}
