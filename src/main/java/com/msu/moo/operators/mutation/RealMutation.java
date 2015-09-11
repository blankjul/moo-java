package com.msu.moo.operators.mutation;

import java.util.List;

import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Random;

public class RealMutation extends AbstractMutation<List<Double>> {

	// ! the probability that a bit is changed -> if null it will be 1 / length
	protected Double mProbability = null;

	// ! range of the results
	protected Double[] range = new Double[] { Double.MIN_VALUE, Double.MAX_VALUE };

	//! distribution index
	protected double eta_m = 20.0;

	// ! random generator for this crossover
	protected Random r = Random.getInstance();

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


	protected Double sbxMutation(double p, double lowerBound, double upperBound) {
		double u = r.nextDouble();
		
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
	protected void mutate_(List<Double> b) {
		mProbability = 1 / (double) b.size();
		for (int j = 0; j < b.size(); j++) {
			if (r.nextDouble() < mProbability) {
				b.set(j, sbxMutation(b.get(j), range[0], range[1]));
			}
		}
	}
	

	public void setRange(Double[] range) {
		this.range = range;
	}

}
