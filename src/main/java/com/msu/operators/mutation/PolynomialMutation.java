package com.msu.operators.mutation;

import java.util.List;

import com.msu.operators.AbstractMutation;
import com.msu.util.Random;


public class PolynomialMutation extends AbstractMutation<List<Double>> {

	//! the probability that a bit is changed
	protected Double probability = null;
	
	//! range of the results
	protected Double[] range = new Double[] {Double.MIN_VALUE, Double.MAX_VALUE};
	
	
	/**
	 * Normal constructor with dynamic probability 
	 */
	public PolynomialMutation() {
		super();
	}
	

	/**
	 * Constructor which fixes the probability for all inputs.
	 * @param probability that a bit is changed
	 */
	public PolynomialMutation(Double probability) {
		super();
		this.probability = probability;
	}
	

	public PolynomialMutation(Double[] range) {
		super();
		this.range = range;
	}


	@Override
	protected void mutate_(List<Double> b, Random rand) {
		if (probability == null) probability = 1 / (double) b.size();
		for (int i = 0; i < b.size(); i++) {
			if (rand.nextDouble() < probability) {
				b.set(i, rand.nextDouble(range[0], range[1]));
			}
		}
	}
	

	public void setRange(Double[] range) {
		this.range = range;
	}


}
