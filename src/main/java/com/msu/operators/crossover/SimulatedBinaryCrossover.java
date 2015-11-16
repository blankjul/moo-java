package com.msu.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.operators.AbstractListCrossover;
import com.msu.util.Pair;
import com.msu.util.Random;
import com.msu.util.Range;

public class SimulatedBinaryCrossover extends AbstractListCrossover<Double> {

	// ! minimal difference for executing the simulated binary crossover
	protected double EPS = 1.0e-30;

	// boundaries for the values
	protected Range<Double> range = null;

	//! distribution index
	protected double eta_c = 20.0;
	
	//! crossover probability in the list
	protected double cProbability = 0.5;


	public SimulatedBinaryCrossover() {
		super();
	}
	
	public SimulatedBinaryCrossover(Range<Double> range) {
		super();
		this.range = range;
	}

	/**
	 * Calculate like
	 * 
	 * @param u
	 *            random variable between [0,1]
	 * @param alpha
	 * @return beta value
	 */
	protected double calcBeta(double u, double alpha, double eta_c) {
		if (u <= 0.5 / (1 - alpha))
			return Math.pow(2 * u * (1 - alpha), (1.0 / (eta_c + 1.0)));
		else
			return 1 / Math.pow(2 * (1 - u * (1 - alpha)), (1.0 / (eta_c + 1.0)));
	}

	/**
	 * p1 < p2 has to be true
	 * @param p1 first parent
	 * @param p2 second parent
	 * @param lowerBound the lower bound which should hold for the child
	 * @return alpha value for the lower child
	 */
	protected double calcAplhaLower(double p1, double p2, double lowerBound) {
		return 0.5 / (1 + 2 * (p1 - lowerBound) / (p2 - p1));
	}

	/**
	 * p1 < p2 has to be true
	 * @return alpha value for the upper child
	 */
	protected double calcAplhaUpper(double p1, double p2, double upperBound) {
		return 0.5 / (1 + 2 * (upperBound - p2) / (p2 - p1));
	}

	/**
	 * SBX for two double variables
	 * @return offspring values which are in [lowerBound,upperBound]
	 */
	protected Pair<Double, Double> SBX(double d1, double d2, double lowerBound, double upperBound, Random rand) {

		// p1 <= p2 is always true
		double p1 = Math.min(d1, d2);
		double p2 = Math.max(d1, d2);

		// left child
		double leftBeta = calcBeta(rand.nextDouble(), calcAplhaLower(p1, p2, lowerBound), eta_c);
		double c1 = (p1 + p2) / 2 - leftBeta * (p1 + p2) / 2;

		// right child
		double rightBeta = calcBeta(rand.nextDouble(), calcAplhaUpper(p1, p2, upperBound), eta_c);
		double c2 = (p1 + p2) / 2 + rightBeta * (p1 + p2) / 2;

		// to avoid floating point problems set boundaries.
		if (c1 < lowerBound)
			c1 = lowerBound;
		if (c2 > upperBound)
			c2 = upperBound;

		return Pair.create(c1, c2);
	}

	@Override
	protected List<List<Double>> crossoverLists(List<Double> parent1, List<Double> parent2, Random rand) {

		List<Double> child1 = new ArrayList<Double>(parent1);
		List<Double> child2 = new ArrayList<Double>(parent2);
		
		if (range == null) range = new Range<Double>(parent1.size(), Double.MIN_VALUE, Double.MAX_VALUE);

		// for each double value in the list
		for (int i = 0; i < parent1.size(); i++) {

			// perform crossover if sbxProbability is given
			if (rand.nextDouble() <= cProbability) {

				// get the parent values
				double p1 = parent1.get(i);
				double p2 = parent2.get(i);

				// if the difference is very small -> no crossover
				if (Math.abs(p1 - p2) <= EPS) continue;

				// perform sbx for two double variables
				Pair<Double, Double> children = SBX(p1, p2, range.getMinimum(i), range.getMaximum(i), rand);

				// set the offspring randomly to the children vectors
				if (rand.nextDouble() <= 0.5) {
					child1.set(i, children.first);
					child2.set(i, children.second);
				} else {
					child1.set(i, children.second);
					child2.set(i, children.first);
				}
			}
		}
		return new ArrayList<>(Arrays.asList(child1, child2));
	}

}