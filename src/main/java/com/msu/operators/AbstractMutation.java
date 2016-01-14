package com.msu.operators;

import com.msu.interfaces.IMutation;
import com.msu.interfaces.IVariable;

/**
 * This is an abstract Mutation of an object. This class which inherits from this one
 * could specify which type is expected to mutate. Otherwise there will be an error thrown.
 *
 * @param <T> type which could be mutated!
 */
public abstract class AbstractMutation<T> extends AbstractOperator implements IMutation {
	
	
	public void mutate(IVariable a) {
		
		try {
			@SuppressWarnings("unchecked")
			T entry = (T) a.get();
			mutate_(entry);
		} catch (ClassCastException e){
			System.out.println(a.getClass().getName());
			System.out.println(a);
			e.printStackTrace();
			throw new RuntimeException("Mutation could not be performed. Wrong IVariable for mutation!");
		}

	}

	
	abstract public void mutate_(T element);


}