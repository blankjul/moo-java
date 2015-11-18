package com.msu.model.variables;

import java.util.List;

public abstract class ListVariable<T> extends Variable<List<T>>{
	

	public ListVariable(List<T> obj) {
		super(obj);
	}


	public String toString() {
		return obj.toString();
	}

	
	public T get(int i) {
		return obj.get(i);
	}
	

	public int size() {
		return obj.size();
	}

	
}
