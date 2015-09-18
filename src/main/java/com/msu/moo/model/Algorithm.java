package com.msu.moo.model;

import com.msu.moo.algorithms.IMultiObjectiveAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface 
 *
 * @param <V>
 * @param <P>
 */
public abstract class Algorithm<V extends IVariable, P extends IProblem> implements IMultiObjectiveAlgorithm<P> {

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
