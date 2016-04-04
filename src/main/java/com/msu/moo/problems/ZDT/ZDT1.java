package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.Range;

/**
 * Class representing problem ZDT1
 */
public class ZDT1 extends AbstractZDT  {

	/**
	 * Returns the value of the ZDT1 function G.
	 * 
	 * @param decisionVariables
	 *            The decision variables of the solution to evaluate.
	 */
	private double evalG(List<Double> x) {
		double g = 0;
		for (int i = 1; i < x.size(); i++) {
			g += x.get(i);
		}
		
		double c = (9.0 / (x.size() - 1));
		return 1 + c * g;
	} 
	

	/**
	 * Returns the value of the ZDT1 function H.
	 * 
	 * @param f
	 *            First argument of the function H.
	 * @param g
	 *            Second argument of the function H.
	 */
	public double evalH(double f, double g) {
		if (f == g) {
			System.out.println();
		}
		return 1.0 - Math.sqrt(f / g);
	} 


	
	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives, List<Double> constraintViolations) {

		constraintViolations.add(calcRangeViolation(var, getVariableConstraints()));
		
		final double f = var.get(0);
		objectives.add(f);
		
		final double g = evalG(var.decode());
		final double h = evalH(f, g);
		objectives.add(h);
		
	}
	


	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(30, 0d, 1d);
	}


	
}
