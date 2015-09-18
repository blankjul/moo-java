package com.msu.moo.model.solution;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.ASolution;

public class SingleObjectiveSolution extends ASolution<Double> {

	public SingleObjectiveSolution(IVariable variable, Double objective) {
		super(variable, objective);
	}

	public int countObjectives() {
		return 1;
	}

}
