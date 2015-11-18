package com.msu.operators;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.util.Random;

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
	public List<IVariable> crossover(IVariable a, IVariable b, IProblem problem, Random rand) {

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
		if (problem == null) offspring = crossover_(first,second, problem, rand);
		else offspring = crossover_(first,second, problem, rand);
		
		
		List<IVariable> result = new ArrayList<>();
		for (T o : offspring) {
			IVariable prototype = a.copy();
			prototype.set(o);
			result.add(prototype);
		}

		return result;

	}
	
	public List<IVariable> crossover(IVariable a, IVariable b, Random rand) {
		return crossover(a, b, null, rand);
	}
	

	public <V extends IVariable, P extends IProblem> List<Solution> crossover(P problem, Solution a,
			Solution b, Random rand) {

		List<IVariable> vars = crossover(a.getVariable(), b.getVariable(), problem, rand);
		List<Solution> result = new ArrayList<>();

		for (IVariable var : vars) {
			Solution s = problem.evaluate(var);
			result.add(s);
		}

		return result;
	}
	

	abstract protected List<T> crossover_(T a, T b, IProblem problem, Random rand);
	

}
