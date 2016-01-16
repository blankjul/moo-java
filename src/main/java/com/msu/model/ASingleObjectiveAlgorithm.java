package com.msu.model;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.ISingleObjectiveAlgorithm;
import com.msu.interfaces.IVariable;

public abstract class ASingleObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> implements ISingleObjectiveAlgorithm<V,P>  {

	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}




	

	

}
