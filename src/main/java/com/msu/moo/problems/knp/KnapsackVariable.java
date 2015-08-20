package com.msu.moo.problems.knp;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.interfaces.IVariable;

public class KnapsackVariable implements IVariable<List<Boolean>> {

	protected List<Boolean> plan = new ArrayList<>();

	public KnapsackVariable(List<Boolean> plan) {
		super();
		this.plan = plan;
	}

	@Override
	public List<Boolean> get() {
		return plan;
	}

}
