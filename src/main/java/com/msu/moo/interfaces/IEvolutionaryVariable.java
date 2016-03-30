package com.msu.moo.interfaces;

public interface IEvolutionaryVariable<T, V extends IEvolutionaryVariable<T,V>> extends IVariable {


	/**
	 * @return copy the current variable efficiently
	 */
	public T decode();
	
	public V build(T obj);
	
	
}
