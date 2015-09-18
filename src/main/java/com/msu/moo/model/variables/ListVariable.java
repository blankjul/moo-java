package com.msu.moo.model.variables;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AVariable;

public class ListVariable<T> extends AVariable<List<T>>{
	

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
