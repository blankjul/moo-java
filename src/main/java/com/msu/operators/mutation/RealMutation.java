package com.msu.operators.mutation;

import java.util.List;

import com.msu.operators.AbstractMutation;
import com.msu.util.Random;

public class RealMutation extends AbstractMutation<List<Double>> {

	// ! the probability that a bit is changed -> if null it will be 1 / length
	protected Double mProbability = null;

	// ! range of the results
	protected Double[] range = new Double[] { Double.MIN_VALUE, Double.MAX_VALUE };

	//! distribution index
	protected double eta_m = 20.0;


	/**
	 * Normal constructor with dynamic probability
	 */
	public RealMutation() {
		super();
	}

	public RealMutation(Double[] range) {
		super();
		this.range = range;
	}
	
	public RealMutation(Double[] range, double eta_m) {
		this(range);
		this.eta_m = eta_m;
	}


	protected Double sbxMutation(double p, double u, double lowerBound, double upperBound) {
		
		Double offspring = null;
		
		if (u < 0.5) {
			double deltaLower = Math.pow(2 * u, 1 / (1 + eta_m));
			offspring =  p + deltaLower * (p - lowerBound);
		} else {
			double deltaUpper = 1 - Math.pow(2 * (1-u), 1 / (1 + eta_m));
			offspring =  p + deltaUpper * (upperBound - p);
		}
		
		// for sure in bounds. floating point uncertainty...
		if (offspring < lowerBound) offspring = lowerBound;
		if (offspring > upperBound) offspring = upperBound;
		
		return offspring;
	}

	@Override
	protected void mutate_(List<Double> b, Random rand) {
		mProbability = 1 / (double) b.size();
		for (int j = 0; j < b.size(); j++) {
			if (rand.nextDouble() < mProbability) {
				double u = rand.nextDouble();
				b.set(j, sbxMutation(b.get(j), u, range[0], range[1]));
			}
		}
	}
	

	public void setRange(Double[] range) {
		this.range = range;
	}

}
