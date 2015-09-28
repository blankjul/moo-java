package com.msu.moo.interfaces;

/**
 * This class represents a factory class that should create a IVariable or
 */
public interface IVariableFactory {

	/**
	 * Create a new object from the class. The problem information are provided
	 * if needed.
	 * 
	 * @return new random instance.
	 */
	public IVariable next(IProblem problem);

	/**
	 * @return true if a new solution could be created
	 */
	public boolean hasNext();

}
