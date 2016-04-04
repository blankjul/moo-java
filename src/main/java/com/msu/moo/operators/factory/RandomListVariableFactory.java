package com.msu.moo.operators.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.model.variable.ListVariable;
import com.msu.moo.util.MyRandom;

public class RandomListVariableFactory<T> implements IFactory<ListVariable<T>>{

	// ! all entries to randomly select from
	protected Function<MyRandom, T> func = null;
	
	protected int length = -1;

	public RandomListVariableFactory(List<T> entries, int length) {
		super();
		func = (r -> r.select(entries));
		this.length = length;
	}

	public RandomListVariableFactory(Function<MyRandom, T> func, int length) {
		this.func = func;
		this.length = length;
	}

	@Override
	public ListVariable<T> next(MyRandom rand) {
		List<T> l = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			l.add(func.apply(rand));
		}
		return new ListVariable<>(l);
	}

}
