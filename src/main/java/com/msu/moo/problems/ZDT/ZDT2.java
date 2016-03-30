
package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.Range;


public class ZDT2 extends AbstractZDT {


	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {
		
		List<Double> v = var.decode();
		
		double g = 0.0;
		for (int i = 1; i < v.size(); i++) {
			g += v.get(i);
		}
		g = (9.0 / (v.size() - 1)) * g + 1.0;

		double h = 1.0 - Math.pow(v.get(0) / g, 2.0);
		
		objectives.add(v.get(0));
		objectives.add(g * h);
		
	}
	

	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(30, 0d, 1d);
	}


}
