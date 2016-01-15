package com.msu.model;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.ISingleObjectiveAlgorithm;
import com.msu.interfaces.IVariable;
import com.msu.util.MyRandom;

public abstract class ASingleObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements ISingleObjectiveAlgorithm<V,P>  {

	
	protected P problem;
	
	protected IEvaluator<V, P> evaluator;
	
	protected MyRandom rand;
	
	@Override
	public ISingleObjectiveAlgorithm<V, P> initialize(P problem, IEvaluator<V, P> evaluator, MyRandom rand) {
		this.problem = problem;
		this.evaluator = evaluator;
		this.rand = rand;
		return this;
	}



	@Override
	public String toString() {
		return getClass().getSimpleName();
	}




	

	

}
