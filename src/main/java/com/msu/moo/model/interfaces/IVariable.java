package com.msu.moo.model.interfaces;

/**
 * Interface for any variable that could be used. 
 *
 */
public interface IVariable {
	
	/**
	 * @return the variable by it self
	 */
	public Object get();
	
	/**
	 * Set the value for the variable
	 */
	public void set(Object obj);
	
	/**
	 * @return copy of the current variable
	 */
	public IVariable copy();
	
	
	
}