package com.msu.operators;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.util.Random;

/**
 * This is an abstract Mutation of an object. This class which inherits from this one
 * could specify which type is expected to mutate. Otherwise there will be an error thrown.
 *
 * @param <T> type which could be mutated!
 */
public abstract class AbstractMutation<T> {


	public IVariable mutate(IVariable a, Random rand) {

		try {
			
			IVariable result = a.copy();
			
			@SuppressWarnings("unchecked")
			T entry = (T) result.get();
			
			mutate_(entry, rand);
			return result;
			
		} catch (Exception e){
			System.out.println(a.getClass().getName());
			System.out.println(a);
			e.printStackTrace();
			throw new RuntimeException("Mutation could not be performed. Wrong IVariable types!");
		}

	}

	public <V extends IVariable, P extends IProblem> Solution mutate(P problem, Solution a, Random rand) {
		IVariable var = mutate(a.getVariable(), rand);
		return problem.evaluate(var);
	}
	
	
	abstract protected void mutate_(T element, Random rand);


}
