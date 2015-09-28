package com.msu.moo.algorithms;

/**
 * An AbstractAlgorithm implements the IAlgorithm interface 
 *
 * @param <V>
 * @param <P>
 */
public abstract class AbstractAlgorithm implements IAlgorithm {

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
