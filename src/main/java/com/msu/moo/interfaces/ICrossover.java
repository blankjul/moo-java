package com.msu.moo.interfaces;

import java.util.List;

import com.msu.moo.util.MyRandom;

/**
 * @param <V> Variable that should be recombined.
 */
public interface ICrossover<V extends IEvolutionaryVariable<?>> {

	/**
	 * Perform a crossover on two individuals.
	 * 
	 * @param a first individual
	 * @param b second individual
	 * @param rand according this random number generator
	 * @return List of offsprings
	 */
	public List<V> crossover(V a, V b, MyRandom rand);

	
}
