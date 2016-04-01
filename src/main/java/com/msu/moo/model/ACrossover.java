package com.msu.moo.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.util.MyRandom;

/**
 * This abstract crossover allows to ignore the variable classes and only
 * develop a method for the decoded types. Normally the variables needs to be
 * decoded and a new variable needs to be build in order to create the
 * offsprings. These steps should be abstracted by this class.
 *
 * @param <T>
 *            decoded variable
 * @param <V>
 *            variable type
 */
public abstract class ACrossover<T, V extends IEvolutionaryVariable<T, V>> implements ICrossover<V> {

	/**
	 * Crossover of the decoded variable contents
	 * @param a first decoded variable 
	 * @param b second decoded variable 
	 * @param rand
	 * @return List of offspring decodings.
	 */
	public abstract List<T> crossover(T a, T b, MyRandom rand);

	
	@Override
	public List<V> crossover(V a, V b, MyRandom rand) {
		
		// executing abstract method
		List<T> offspring = crossover(a.decode(), b.decode(), rand);

		// create variables
		List<V> result = new ArrayList<>();
		for (T off : offspring) {
			V var = a.build(off);
			result.add(var);
		}

		return result;
	}

}
