package com.msu.interfaces;

/**
 * This class represents a factory class that could construct new
 * variable randomly.
 *
 * @param <T> variable type
 */
public interface IFactory {
	
	/**
	 * Create a new object from the class.
	 * @return new random instance.
	 */
	public IVariable next();
	
	public boolean hasNext();

}
