package com.msu.moo.model.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.IFactory;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Range;

public class DoubleListVariableFactory implements IFactory<DoubleListVariable> {
	
	//! length of the vector
	protected Range<Double> range = null;
		

	public DoubleListVariableFactory(Range<Double> range) {
		this.range = range;
	}
	
	public DoubleListVariableFactory(int length, double[] range) {
		this.range = new Range<Double>(length, range[0], range[1]);
	}

	@Override
	public DoubleListVariable next(MyRandom rand) {
		List<Double> l = new ArrayList<>();
		for (int i = 0; i < range.size(); i++) {
			l.add(rand.nextDouble(range.getMinimum(i), range.getMaximum(i)));
		}
		return new DoubleListVariable(l);
	}


}
