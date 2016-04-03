package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.ASingleObjectiveProblem;

public abstract class ASingleObjectiveAlgorithm<V extends IVariable, P extends ASingleObjectiveProblem<V>> implements ISingleObjectiveAlgorithm<V,P>  {

	protected String name;
	
	
	public ASingleObjectiveAlgorithm() {
		super();
		name = getClass().getSimpleName();
	}


	@Override
	public String toString() {
		return name;
	}



}
