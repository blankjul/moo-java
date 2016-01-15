package com.msu.moo.problems;

import java.util.List;

import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.util.Range;

public class Kursawe extends DoubleVariableListProblem {

	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {

		double aux, xi, xj;
		double[] fx = new double[2];
		double[] x = new double[3];
		x[0] = var.decode().get(0);
		x[1] = var.decode().get(1);
		x[2] = var.decode().get(2);

		fx[0] = 0.0;
		for (int i = 0; i < var.decode().size() - 1; i++) {
			xi = x[i] * x[i];
			xj = x[i + 1] * x[i + 1];
			aux = (-0.2) * Math.sqrt(xi + xj);
			fx[0] += (-10.0) * Math.exp(aux);
		}

		fx[1] = 0.0;

		for (int i = 0; i < var.decode().size(); i++) {
			fx[1] += Math.pow(Math.abs(x[i]), 0.8) + 5.0 * Math.sin(Math.pow(x[i], 3.0));
		}

		objectives.add(fx[0]);
		objectives.add(fx[1]);
	}

	
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(3, -5d, 5d);
	}


	
	
	@Override
	public int getNumberOfObjectives() {
		return 2;
	}
	


	@Override
	public int getNumberOfConstraints() {
		return 3;
	}








}
