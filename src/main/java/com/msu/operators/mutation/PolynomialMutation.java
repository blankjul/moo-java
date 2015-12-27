package com.msu.operators.mutation;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.util.MyRandom;


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
	public List<Double> mutate_(List<Double> b, IProblem problem, MyRandom rand, IEvaluator evaluator) {
		List<Double> result = new ArrayList<>();
		
		if (probability == null) probability = 1 / (double) b.size();
		for (int i = 0; i < b.size(); i++) {
			if (rand.nextDouble() < probability) result.add(rand.nextDouble(range[0], range[1]));
			else result.add(b.get(i));
		}
		return result;
	}
	

	public void setRange(Double[] range) {
		this.range = range;
	}


}
