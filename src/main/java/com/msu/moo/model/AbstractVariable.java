package com.msu.moo.model;

import com.msu.moo.model.interfaces.IVariable;

public abstract class AbstractVariable<T> implements IVariable<T> {

	//! list that contains all the values
	protected T obj;
	
	
	public AbstractVariable(T obj) {
		super();
		this.obj = obj;
	}

	@Override
	public T get() {
		return obj;
	}

	@Override
	public void set(T obj) {
		this.obj = obj;
	}

	@Override
	abstract public IVariable<T> copy();

}
