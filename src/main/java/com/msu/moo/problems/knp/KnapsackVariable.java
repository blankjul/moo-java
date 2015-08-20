package com.msu.moo.problems.knp;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.interfaces.IVariable;

public class KnapsackVariable implements IVariable {
	

	protected List<Boolean> plan = new ArrayList<>();
	
	public List<Boolean> getPlan() {
		return plan;
	}

	public KnapsackVariable(List<Boolean> plan) {
		super();
		this.plan = plan;
	}
	
	
	
}
