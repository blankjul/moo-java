package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.model.variables.DoubleListVariable;
import com.msu.util.Range;

public class ZDT6 extends AbstractZDT {

	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {

		double f = 1.0 - Math.exp(-4.0 * var.get(0)) * Math.pow(Math.sin(6.0 * Math.PI * var.get(0)), 6.0);

		double g = 0.0;
		for (int i = 1; i < var.size(); i++) {
			g += var.get(i);
		}
		g = 1.0 + 9.0 * Math.pow(g / (var.size() - 1), 0.25);

		double h = 1.0 - Math.pow(f / g, 2.0);

		objectives.add(f);
		objectives.add(g * h);

	}

	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(10, 0d, 1d);
	}


}
