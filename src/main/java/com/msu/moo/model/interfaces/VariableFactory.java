package com.msu.moo.model.interfaces;

/**
 * This class represents a factory class that should create a IVariable or
 *
 * @param <T> variable type
 */
public interface VariableFactory<V extends IVariable, P extends IProblem> {
	
	/**
	 * Create a new object from the class. The problem information are provided if needed.
	 * @return new random instance.
	 */
	public V create(P problem);
	

}
