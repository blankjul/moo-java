package com.msu.moo.model.variable;

import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.util.Util;

/**
 * This is a template for variable where the encoding and decoding is the same.
 * However, this might be the case for many variables it is not a must.
 * 
 * NOTE: If this class is inherited PLEASE override
 * 
 *				public IVariable<T> copy() - more efficient without reflection.
 *
 *
 * @param <T>
 *            decoded AND encoded type of variable.
 */
public class Variable<T> extends AVariable<T> implements IEvolutionaryVariable<T, Variable<T>> {


	public Variable(T obj) {
		super(obj);
	}

	/**
	 * default copy which is pretty expensive using cloneObject and reflection.
	 * BUT: this could be used for any variable.
	 */
	public Variable<T> copy() {
		T copy = Util.cloneObject(obj);
		return new Variable<T>(copy);
	}

	
	@Override
	public Variable<T> build(T obj) {
		return new Variable<T>(obj);
	}
	


}