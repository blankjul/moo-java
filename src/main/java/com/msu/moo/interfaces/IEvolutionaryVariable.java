package com.msu.moo.interfaces;

/**
 * Every variable that can be used for for evolutionary algorithms has to
 * implement this interface. The build method is need to create a new variable
 * from the decoding.
 *
 * @param <T>
 *            Decoding type for evolution process
 * @param <V>
 *            Variable of type V
 */
public interface IEvolutionaryVariable<T> extends IVariable {

	/**
	 * @return the decoding which is used for the recombinations.
	 */
	public T decode();

	/**
	 * OVERRIDE this method always. It needs to return the same type of object
	 * as the class is!
	 * 
	 * e.g. class DoubleListVariable: the build method is called. build must
	 * also return an object of DoubleListVariable. Here the
	 * IEvolutionaryVariable<T> is not enough.
	 * 
	 * @param decoded
	 *            variable content
	 * @return variable with that content. TYPE is the inherited class.
	 */
	public IEvolutionaryVariable<T> build(T obj);

}
