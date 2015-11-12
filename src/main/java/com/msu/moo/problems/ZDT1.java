package com.msu.moo.problems;

import java.util.List;

import com.msu.model.AProblem;
import com.msu.model.variables.DoubleListVariable;

/**
 * Class representing problem ZDT1
 */
public class ZDT1 extends AProblem<DoubleListVariable> {

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
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	protected void evaluate_(DoubleListVariable var, List<Double> objectives, List<Double> constraintViolations) {
		double[] f = new double[getNumberOfObjectives()];
		f[0] = var.get().get(0);
		double g = this.evalG(var.get());
		double h = this.evalH(f[0], g);
		f[1] = h * g;
		
		objectives.add(f[0]);
		objectives.add(f[1]);
	}
}
