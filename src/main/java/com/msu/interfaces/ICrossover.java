package com.msu.interfaces;

import java.util.List;

import com.msu.util.MyRandom;

/**
 * @param <T> decoded variable type
 */
public interface ICrossover<V extends IVariable, P extends IProblem<V>> {

	public List<V> crossover(P problem, MyRandom rand, V a, V b);

}
