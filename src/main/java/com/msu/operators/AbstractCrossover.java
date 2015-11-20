package com.msu.operators;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.util.MyRandom;

/**
 * This is an abstract Crossover of an object. This class which inherits from
 * this one could specify which type is expected to be used to crossover.
 * Otherwise there will be an error thrown.
 *
 * @param <T>
 *            type which could be mutated!
 */
public abstract class AbstractCrossover<T> {

	

	@SuppressWarnings("unchecked")
	public List<IVariable> crossover(IVariable a, IVariable b, IProblem problem, MyRandom rand) {

		// TODO: This solution is very ugly. could be improved but all crossover
		// subclasses has to be changed.
		T first = null;
		T second = null;
		
		try {
			
			first = (T) a.get();
		    second = (T) b.get();
			
		} catch (Exception e){
			throw new RuntimeException("Crossover could not be performed. Wrong IVariable types!");
		}
		 
		// if problem is null call the normal crossover else specific one
		List<T> offspring = null;
		if (problem == null) offspring = crossover_(first, second, problem, rand);
		else offspring = crossover_(first,second, problem, rand);
		
		
		List<IVariable> result = new ArrayList<>();
		for (T o : offspring) {
			IVariable prototype = a.copy();
			prototype.set(o);
			result.add(prototype);
		}

		return result;

	}
	
	public List<IVariable> crossover(IVariable a, IVariable b, MyRandom rand) {
		return crossover(a, b, null, rand);
	}
	


	abstract protected List<T> crossover_(T a, T b, IProblem problem, MyRandom rand);
	

}
