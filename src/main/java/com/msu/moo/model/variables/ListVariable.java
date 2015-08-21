package com.msu.moo.model.variables;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;

public class ListVariable<T> extends AbstractVariable<List<T>>{
	

	public ListVariable(List<T> obj) {
		super(obj);
	}


	public String toString() {
		return obj.toString();
	}


	@Override
	public IVariable<List<T>> copy() {
		return new ListVariable<>(new ArrayList<>(obj));
	}
	

}
