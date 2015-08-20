package com.msu.moo.model.interfaces;

/**
 * Interface for any variable that could be used. 
 *
 */
public interface IVariable<T> {
	
	/**
	 * @return the variable by it self
	 */
	public T get();
}