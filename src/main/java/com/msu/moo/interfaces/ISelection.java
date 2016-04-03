package com.msu.moo.interfaces;

import java.util.List;

import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.MyRandom;

/**
 * Selection of an individuals from a population. 
 *
 * @param <V> variable type to select
 * @param <S> solution with variable type V
 */
public interface ISelection<S extends ISolution<?>> {

	/**
	 * 
	 * @param population which individuals should be selected of
	 * @param n number of individuals to select
	 * @param rand randomness if needed
	 * @return List of solutions which were selected
	 */
	public SolutionSet<S> next(List<S> population, int n, MyRandom rand);


}
