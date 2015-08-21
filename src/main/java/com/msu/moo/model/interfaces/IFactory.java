package com.msu.moo.model.interfaces;

/**
 * This class represents a factory class that could construct new
 * variable randomly.
 *
 * @param <T> variable type
 */
public interface IFactory<T extends IVariable> {
	
	/**
	 * Create a new object from the class.
	 * @return new random instance.
	 */
	public T create();
	

}
