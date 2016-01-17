package com.msu.moo.model.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IEvolutionaryVariable;

public class ListVariable<T> extends AVariable<List<T>> implements IEvolutionaryVariable<List<T>, ListVariable<T>>{


	public ListVariable(List<T> obj) {
		super(obj);
	}


	@Override
	public ListVariable<T> copy() {
		return new ListVariable<T>(new ArrayList<T>(obj));
	}


	@Override
	public ListVariable<T> build(List<T> obj) {
		return new ListVariable<T>(obj);
	}


	
	
	
	
}
