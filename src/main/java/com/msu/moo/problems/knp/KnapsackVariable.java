package com.msu.moo.problems.knp;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;

public class KnapsackVariable extends AbstractVariable<List<Boolean>> {

	public KnapsackVariable(List<Boolean> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new KnapsackVariable(new ArrayList<Boolean>(obj));
	}

}
