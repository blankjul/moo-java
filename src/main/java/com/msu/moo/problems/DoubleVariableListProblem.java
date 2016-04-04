package com.msu.moo.problems;

import java.util.List;

import com.msu.moo.model.AProblem;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.Range;
import com.msu.moo.util.exceptions.EvaluationException;

public abstract class DoubleVariableListProblem extends AProblem<DoubleListVariable> {

	protected abstract void evaluate__(DoubleListVariable var, List<Double> objectives, List<Double> constraintViolations);

	public abstract Range<Double> getVariableConstraints();

	@Override
	protected void evaluate_(DoubleListVariable var, List<Double> objectives, List<Double> constraintViolations) {

		// check if the right number of variables are provided.
		if (var.decode().size() != getNumberOfDoubleVariable())
			throw new EvaluationException(
					String.format("Variable needs to have %s entries, but %s are provided.", getNumberOfDoubleVariable(), var.decode().size()));

		// call the evaluation method
		evaluate__(var, objectives, constraintViolations);

	}

	@Override
	public int getNumberOfConstraints() {
		return 1;
	}

	
	public static double calcRangeViolation(DoubleListVariable var, Range<Double> constraints) {

		// add all the constraint violations.
		
		double violation = 0;

		for (int i = 0; i < constraints.size(); i++) {

			double value = var.decode().get(i);

			if (value < constraints.getMinimum(i))
				violation += Math.abs(constraints.getMinimum(i) - value);
			else if (value > constraints.getMaximum(i))
				violation += Math.abs(value - constraints.getMaximum(i));
		}
		return violation;
	}

	public int getNumberOfDoubleVariable() {
		return getVariableConstraints().size();
	};

}
