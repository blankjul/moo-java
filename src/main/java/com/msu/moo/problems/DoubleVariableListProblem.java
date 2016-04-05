package com.msu.moo.problems;

import com.msu.moo.model.AProblem;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.Range;

/**
 * This class represents a DoubleListProblem. A list of doubles is given in
 * order to evaluate. There is also a range where the values have to lie in.
 * 
 * f(x,y) = ....
 * 
 * Many test function could be created by inheriting from this class.
 *
 */
public abstract class DoubleVariableListProblem extends AProblem<DoubleListVariable> {

	// ! range of the problem
	protected Range<Double> range = null;

	public DoubleVariableListProblem(Range<Double> range) {
		this.range = range;
	}

	public double calcRangeViolation(DoubleListVariable var) {

		if (range == null)
			return 0.0;

		// add all the constraint violations.
		double violation = 0;

		for (int i = 0; i < range.size(); i++) {

			double value = var.decode().get(i);

			if (value < range.getMinimum(i))
				violation += Math.abs(range.getMinimum(i) - value);
			else if (value > range.getMaximum(i))
				violation += Math.abs(value - range.getMaximum(i));
		}
		return violation;
	}

	public Range<Double> getRange() {
		return range;
	};

}
