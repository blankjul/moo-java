package com.msu.moo.interfaces;

import com.msu.moo.util.MyRandom;

/**
 * This class represents a factory class that could construct new variable
 * randomly.
 *
 * @param <V>
 *            variable
 */
public interface IFactory<V extends IVariable> {

	/**
	 * Create a new object from the class.
	 * 
	 * @return new random instance.
	 */
	public V next(MyRandom rand);


}
