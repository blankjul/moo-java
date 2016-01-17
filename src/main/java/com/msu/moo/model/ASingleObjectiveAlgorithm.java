package com.msu.moo.model;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.algorithms.ISingleObjectiveAlgorithm;

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
