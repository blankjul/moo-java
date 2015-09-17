package com.msu.moo.interfaces;

/**
 * This class represents a factory class that should create a IVariable or
 *
 * @param <T> variable type
 */
public interface IVariableFactory<V extends IVariable, P extends IProblem> {
	
	/**
	 * Create a new object from the class. The problem information are provided if needed.
	 * @return new random instance.
	 */
	public V next(P problem);
	
	/**
	 * @return true if a new solution could be created
	 */
	public boolean hasNext();

}
