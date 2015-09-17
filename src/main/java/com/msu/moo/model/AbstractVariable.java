package com.msu.moo.model;

import com.msu.moo.interfaces.IVariable;

public abstract class AbstractVariable<T> implements IVariable {

	//! list that contains all the values
	protected T obj;
	
	abstract public IVariable copy();
	
	public AbstractVariable(T obj) {
		super();
		this.obj = obj;
	}

	@Override
	public T get() {
		return obj;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void set(Object obj) {
		try {
			this.obj = (T) obj;
		} catch (Exception e){
			throw new RuntimeException("Object could not be set for variable");
		}
		
	}
	

	
	@Override
	public String toString() {
		return obj.toString();
	}


}
