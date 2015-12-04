package com.msu.model.variables;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IVariable;

public class ListVariable<T> extends Variable<List<T>>{
	

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


	@Override
	public IVariable copy() {
		return new ListVariable<>(new ArrayList<>(obj));
	}
	
	

	
}
