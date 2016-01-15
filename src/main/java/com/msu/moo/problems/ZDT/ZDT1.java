package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.util.Range;

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
		double g = 0.0;
		for (int i = 1; i < x.size(); i++)
			g += x.get(i);
		double constante = (9.0 / (x.size() - 1));
		g = constante * g;
		g = g + 1.0;
		return g;
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
		double h = 0.0;
		h = 1.0 - java.lang.Math.sqrt(f / g);
		return h;
	} 


	
	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {
		double[] f = new double[getNumberOfObjectives()];
		f[0] = var.decode().get(0);
		double g = this.evalG(var.decode());
		double h = this.evalH(f[0], g);
		f[1] = h * g;
		
		objectives.add(f[0]);
		objectives.add(f[1]);
	}
	


	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(30, 0d, 1d);
	}


	
}
