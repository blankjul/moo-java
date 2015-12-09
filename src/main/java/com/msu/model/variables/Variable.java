package com.msu.model.variables;



import com.msu.interfaces.IVariable;
import com.msu.util.Util;

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
	
	
	public IVariable copy() {
		return new Variable<T>(Util.cloneObject(obj));
	}
	
	
	@SuppressWarnings("unchecked")
	public <V extends IVariable> V cast(Class<V> clazz) {
		return (V) this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Variable<?> other = (Variable<?>) obj;
		if (this.obj == null) {
			if (other.obj != null)
				return false;
		} else if (!this.obj.equals(other.obj))
			return false;
		return true;
	}
	
	
	


}