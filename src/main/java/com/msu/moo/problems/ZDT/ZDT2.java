
package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.model.variables.DoubleListVariable;
import com.msu.util.Range;


public class ZDT2 extends AbstractZDT {


	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {
		
		double g = 0.0;
		for (int i = 1; i < var.size(); i++) {
			g += var.get(i);
		}
		g = (9.0 / (var.size() - 1)) * g + 1.0;

		double h = 1.0 - Math.pow(var.get(0) / g, 2.0);
		
		objectives.add(var.get(0));
		objectives.add(g * h);
		
	}
	

	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(30, 0d, 1d);
	}


}
