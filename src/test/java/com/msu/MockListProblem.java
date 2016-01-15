package com.msu;

import java.util.List;

import com.msu.model.AProblem;
import com.msu.moo.model.variable.ListVariable;

public class MockListProblem<T> extends AProblem<ListVariable<T>>{

	@Override
	public int getNumberOfObjectives() {
		return 0;
	}

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	protected void evaluate_(ListVariable<T> var, List<Double> objectives, List<Double> constraintViolations) {
		
	}


}
