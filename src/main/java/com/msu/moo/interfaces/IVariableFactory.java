package com.msu.moo.interfaces;

import java.util.Collection;

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
	 * Creates several variables at the same time. Could be useful
	 * when the whole pool of GA should have a special pattern.
	 * 
	 * @return new random instance.
	 */
	public Collection<IVariable> next(IProblem problem, int n);

	/**
	 * @return true if a new solution could be created
	 */
	public boolean hasNext();
	
	

}
