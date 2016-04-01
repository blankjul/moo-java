package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;

public abstract class ASingleObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements ISingleObjectiveAlgorithm<V,P>  {

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
