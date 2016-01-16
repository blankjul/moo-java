package com.msu.interfaces;

import com.msu.util.MyRandom;

/**
 * This class represents a factory class that could construct new
 * variable randomly.
 *
 * @param <T> decoded variable type
 */
public interface IFactory<V extends IVariable> {
	
	/**
	 * Create a new object from the class.
	 * @return new random instance.
	 */
	public V next(MyRandom rand);
	
	public boolean hasNext();

	
}
