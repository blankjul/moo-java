package com.msu.moo.model;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISingleObjectiveAlgorithm;
import com.msu.moo.interfaces.IVariable;

public abstract class ASingleObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements ISingleObjectiveAlgorithm<V,P>  {

	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}




	

	

}
