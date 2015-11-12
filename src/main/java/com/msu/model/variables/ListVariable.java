package com.msu.model.variables;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.model.Variable;

public class ListVariable<T> extends Variable<List<T>>{
	

	public ListVariable(List<T> obj) {
		super(obj);
	}


	public String toString() {
		return obj.toString();
	}


	@Override
	public IVariable copy() {
		return new ListVariable<>(new ArrayList<>(obj));
	}
	

}
