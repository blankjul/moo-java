package com.msu.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.operators.AbstractListCrossover;
import com.msu.moo.util.Random;

public class SimulatedBinaryCrossover extends AbstractListCrossover<Double> {

	public static double EPS = 1.0e-14;

	protected double[] range;

	public double eta_c = 20.0;
	
	

	public SimulatedBinaryCrossover(double[] range) {
		super();
		this.range = range;
	}



	@Override
	protected List<List<Double>> crossoverLists(List<Double> parent1, List<Double> parent2) {

		List<Double> child1 = new ArrayList<Double>(parent1);
		List<Double> child2 = new ArrayList<Double>(parent2);

		int i;
		double rand;
		double y1, y2, yl, yu;
		double c1, c2;
		double alpha, beta, betaq;

		for (i = 0; i < parent1.size(); i++) {
			if (Random.getInstance().nextDouble() <= 0.5) {
				if (Math.abs(parent1.get(i) - parent2.get(i)) > EPS) {
					if (parent1.get(i) < parent2.get(i)) {
						y1 = parent1.get(i);
						y2 = parent2.get(i);
					} else {
						y1 = parent2.get(i);
						y2 = parent1.get(i);
					}
					yl = range[0];
					yu = range[1];
					rand = Random.getInstance().nextDouble();
					beta = 1.0 + (2.0 * (y1 - yl) / (y2 - y1));
					alpha = 2.0 - Math.pow(beta, -(eta_c + 1.0));
					if (rand <= (1.0 / alpha)) {
						betaq = Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
					} else {
						betaq = Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
					}
					c1 = 0.5 * ((y1 + y2) - betaq * (y2 - y1));
					beta = 1.0 + (2.0 * (yu - y2) / (y2 - y1));
					alpha = 2.0 - Math.pow(beta, -(eta_c + 1.0));
					if (rand <= (1.0 / alpha)) {
						betaq = Math.pow((rand * alpha), (1.0 / (eta_c + 1.0)));
					} else {
						betaq = Math.pow((1.0 / (2.0 - rand * alpha)), (1.0 / (eta_c + 1.0)));
					}
					c2 = 0.5 * ((y1 + y2) + betaq * (y2 - y1));
					if (c1 < yl)
						c1 = yl;
					if (c2 < yl)
						c2 = yl;
					if (c1 > yu)
						c1 = yu;
					if (c2 > yu)
						c2 = yu;
					if (Random.getInstance().nextDouble() <= 0.5) {
						child1.set(i, c2);
						child2.set(i, c1);
					} else {
						child1.set(i, c1);
						child2.set(i, c2);
					}
				}
			}

		}
		return new ArrayList<>(Arrays.asList(child1, child2));
	}

}
