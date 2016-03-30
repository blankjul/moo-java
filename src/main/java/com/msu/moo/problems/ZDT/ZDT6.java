package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.Range;

public class ZDT6 extends AbstractZDT {

	// ! could be differ for ZDT6
	protected int numOfVariables = 10;
	
	
	public ZDT6() {
		super();
	}
	

	public ZDT6(int numOfVariables) {
		super();
		this.numOfVariables = numOfVariables;
	}



	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {
		List<Double> v = var.decode();
		
		if (var.size() != numOfVariables) {
			throw new RuntimeException("Wrong number of variables!");
		}

		double f = 1.0 - Math.exp(-4.0 * v.get(0)) * Math.pow(Math.sin(6.0 * Math.PI * v.get(0)), 6.0);

		double g = 0.0;
		for (int i = 1; i < v.size(); i++) {
			g += v.get(i);
		}
		g = 1.0 + 9.0 * Math.pow(g / (v.size() - 1), 0.25);

		double h = 1.0 - Math.pow(f / g, 2.0);

		objectives.add(f);
		objectives.add(g * h);

	}

	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(numOfVariables, 0d, 1d);
	}


}
