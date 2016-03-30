package com.msu.moo.interfaces;

import java.util.List;

import com.msu.moo.util.MyRandom;

/**
 * @param <T> decoded variable type
 */
public interface ICrossover<V extends IVariable> {

	public List<V> crossover(V a, V b, MyRandom rand);

	
}
