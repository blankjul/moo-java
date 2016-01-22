package com.msu.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.model.ACrossover;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Range;

public class SimulatedBinaryCrossover extends ACrossover<List<Double>, DoubleListVariable> {

	// ! minimal difference for executing the simulated binary crossover
	protected double EPS = 1.0e-30;

	// boundaries for the values
	protected Range<Double> range = null;

	// ! distribution index
	protected double eta_c = 20.0;

	// ! crossover probability in the list
	protected double cProbability = 0.5;

	public SimulatedBinaryCrossover(Range<Double> range) {
		super();
		this.range = range;
	}

	@Override
	public List<List<Double>> crossover(List<Double> a, List<Double> b, MyRandom r) {

		List<Double> c1 = new ArrayList<>();
		List<Double> c2 = new ArrayList<>();

		for (int i = 0; i < a.size(); i++) {
			List<Double> offsprings = calc(a.get(i), b.get(i), range.getMinimum(i), range.getMaximum(i), r);
			c1.add(offsprings.get(0));
			c2.add(offsprings.get(1));
		}

		return Arrays.asList(c1, c2);
	}

	protected List<Double> calc(double valueX1, double valueX2, double yL, double yU, MyRandom r) {

		double y1, y2;

		double c1, c2;
		double betaq;

		if (r.nextDouble() <= 0.5) {

			if (Math.abs(valueX1 - valueX2) > EPS) {

				if (valueX1 < valueX2) {
					y1 = valueX1;
					y2 = valueX2;
				} else {
					y1 = valueX2;
					y2 = valueX1;
				} // if

				double rand = r.nextDouble();
				double beta = 1.0 + (2.0 * (y1 - yL) / (y2 - y1));
				double alpha = 2.0 - Math.pow(beta, -(eta_c + 1.0));

				if (rand <= (1.0 / alpha)) {
					betaq = Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
				} else {
					betaq = Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
				} // if

				c1 = 0.5 * ((y1 + y2) - betaq * (y2 - y1));
				beta = 1.0 + (2.0 * (yU - y2) / (y2 - y1));
				alpha = 2.0 - Math.pow(beta, -(eta_c + 1.0));

				if (rand <= (1.0 / alpha)) {
					betaq = Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
				} else {
					betaq = Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
				} // if

				c2 = 0.5 * ((y1 + y2) + betaq * (y2 - y1));

				if (c1 < yL)
					c1 = yL;

				if (c2 < yL)
					c2 = yL;

				if (c1 > yU)
					c1 = yU;

				if (c2 > yU)
					c2 = yU;

				if (r.nextDouble() <= 0.5) {
					return Arrays.asList(c2, c1);
				} else {
					return Arrays.asList(c1, c2);
				} // if

			} else {
				return Arrays.asList(valueX1, valueX2);
			} // if
		} else {
			return Arrays.asList(valueX2, valueX1);
		} // if
	}

}
