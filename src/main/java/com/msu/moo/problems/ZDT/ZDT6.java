package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.Range;

public class ZDT6 extends AbstractZDT {

	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {
		List<Double> v = var.decode();

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
		return new Range<Double>(10, 0d, 1d);
	}


}
