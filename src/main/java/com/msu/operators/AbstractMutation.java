package com.msu.operators;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.util.MyRandom;

/**
 * This is an abstract Mutation of an object. This class which inherits from this one
 * could specify which type is expected to mutate. Otherwise there will be an error thrown.
 *
 * @param <T> type which could be mutated!
 */
public abstract class AbstractMutation<T> {

	
	public IVariable mutate(IVariable a, IProblem problem, MyRandom rand) {
		return mutate(a, problem, rand, null);
	}
	
	
	public IVariable mutate(IVariable a, IProblem problem, MyRandom rand, IEvaluator evaluator) {

		try {
			
			IVariable result = a.copy();
			@SuppressWarnings("unchecked")
			T entry = (T) result.get();
			
			T mutated = mutate_(entry, problem, rand, evaluator);
			result.set(mutated);
			
			return result;
			
		} catch (Exception e){
			System.out.println(a.getClass().getName());
			System.out.println(a);
			e.printStackTrace();
			throw new RuntimeException("Mutation could not be performed. Wrong IVariable types!");
		}

	}

	
	abstract public T mutate_(T element, IProblem problem, MyRandom rand, IEvaluator evaluator);


}