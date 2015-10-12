package com.msu.moo.model;



import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.Util;

public class Variable<T> implements IVariable {

	//! list that contains all the values
	protected T obj;
	

	
	public Variable(T obj) {
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
	

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		try {
			@SuppressWarnings("unchecked")
			T otherObject = (T) ((IVariable) other).get();
			return isEqual(obj, otherObject);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public boolean isEqual(T o1, T o2) {
		return o1.equals(o2);
	}
	
	public IVariable copy() {
		return new Variable<T>(Util.cloneObject(obj));
	}
	
	


}
