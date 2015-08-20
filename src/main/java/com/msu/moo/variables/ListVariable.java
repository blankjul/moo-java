package com.msu.moo.variables;

import java.util.List;

import com.msu.moo.model.interfaces.IVariable;

public class ListVariable<T> implements IVariable<List<T>>{
	
	//! list that contains all the values
	protected List<T> list;

	
	
	public ListVariable(List<T> list) {
		super();
		this.list = list;
	}



	@Override
	public List<T> get() {
		return list;
	}
	
	public String toString() {
		return list.toString();
	}
	

}
