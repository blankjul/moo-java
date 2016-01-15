package com.msu.moo.model.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IFactory;
import com.msu.interfaces.IProblem;
import com.msu.util.MyRandom;
import com.msu.util.Range;

public class DoubleListVariableFactory<P extends IProblem<DoubleListVariable>> implements IFactory<DoubleListVariable, P> {
	
	//! length of the vector
	protected Range<Double> range = null;
		

	public DoubleListVariableFactory(Range<Double> range) {
		this.range = range;
	}
	
	public DoubleListVariableFactory(int length, double[] range) {
		this.range = new Range<Double>(length, range[0], range[1]);
	}

	@Override
	public DoubleListVariable next(P problem, MyRandom rand) {
		List<Double> l = new ArrayList<>();
		for (int i = 0; i < range.size(); i++) {
			l.add(rand.nextDouble(range.getMinimum(i), range.getMaximum(i)));
		}
		return new DoubleListVariable(l);
	}

	@Override
	public boolean hasNext() {
		return true;
	}







}