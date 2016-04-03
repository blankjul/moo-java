package com.msu.moo.model.variable;

/**
 * This is a template for variable where the encoding and decoding is the same.
 * However, this might be the case for many variables it is not a must.
 * 
 * NOTE: If this class is inherited PLEASE override
 * 
 * @param <T>
 *            decoded AND encoded type of variable.
 */
public abstract class AVariable<T>  {

	protected T obj = null;

	
	public AVariable(T obj) {
		super();
		this.obj = obj;
	}

	
	public T decode() {
		return obj;
	}

	public void set(T obj) {
		this.obj = obj;
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
		@SuppressWarnings("unchecked")
		AVariable<T> other = (AVariable<T>) obj;
		if (this.obj == null) {
			if (other.obj != null)
				return false;
		} else if (!this.obj.equals(other.obj))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(obj);
	}
	


}