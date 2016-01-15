package com.msu.moo.problems;

import java.util.List;

import com.msu.model.AProblem;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.util.Range;
import com.msu.util.exceptions.EvaluationException;

public abstract class DoubleVariableListProblem extends AProblem<DoubleListVariable> {
	
	
	protected abstract void evaluate__(DoubleListVariable var, List<Double> objectives);
	
	public abstract Range<Double> getVariableConstraints();
	
	
	@Override
	protected void evaluate_(DoubleListVariable var, List<Double> objectives,
			List<Double> constraintViolations) {
		
		// check if the right number of variables are provided.
		if (var.decode().size() != getNumberOfDoubleVariable()) 
			throw new EvaluationException(String.format("Variable needs to have %s entries, but %s are provided.", getNumberOfDoubleVariable(), var.decode().size()));
		
		// add all the constraint violations.
		Range<Double> constraints = getVariableConstraints();
		for (int i = 0; i < getNumberOfDoubleVariable(); i++) {
			double value = var.decode().get(i);
			if (value < constraints.getMinimum(i)) constraintViolations.add(Math.abs(constraints.getMinimum(i) - value));
			else if (value > constraints.getMaximum(i)) constraintViolations.add(Math.abs(value - constraints.getMaximum(i)));
			else constraintViolations.add(0d);
		}

		// call the evaluation method
		evaluate__(var, objectives);
		
	}

	@Override
	public int getNumberOfConstraints() {
		return getVariableConstraints().size();
	}
	
	
	
	public int getNumberOfDoubleVariable() {
		return getVariableConstraints().size();
	};

	

}
