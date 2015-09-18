package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IProblem;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface 
 *
 * @param <V>
 * @param <P>
 */
public abstract class AbstractAlgorithm<P extends IProblem, S> implements IAlgorithm<S,P> {

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
