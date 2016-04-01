package com.msu.moo.interfaces;

/**
 * Every variable that can be used for for evolutionary algorithms has to
 * implement this interface. The build method is need to create a new variable from the decoding.
 *
 * @param <T> Decoding type for evolution process
 * @param <V> Variable of type V
 */
public interface IEvolutionaryVariable<T, V extends IEvolutionaryVariable<T, V>> extends IVariable {

	/**
	 * @return the decoding which is used for the recombinations.
	 */
	public T decode();

	/**
	 * Build the variable which 
	 * @param decoded variable content
	 * @return variable with that content. TYPE is the inherited class.
	 */
	public V build(T obj);

}
