package com.msu.moo.algorithms.builder;

import com.msu.moo.algorithms.impl.RandomSearch;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.Builder;

public class RandomSearchBuilder <V extends IVariable, P extends IProblem<V>> extends Builder<RandomSearch<V,P>> {

	public RandomSearchBuilder() {
		super(RandomSearch.class);
		propertiesToSet.add("factory");
	}

}
