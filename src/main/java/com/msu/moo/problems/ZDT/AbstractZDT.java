package com.msu.moo.problems.ZDT;

import com.msu.moo.problems.DoubleVariableListProblem;
import com.msu.moo.util.Range;

public abstract class AbstractZDT extends DoubleVariableListProblem {

	public AbstractZDT(Range<Double> range) {
		super(range);
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	
	

}
