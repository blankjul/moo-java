package com.msu.operators.mutation;

import java.util.List;

import com.msu.interfaces.IMutation;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.util.MyRandom;
import com.msu.util.Range;

public class RealMutation implements IMutation<DoubleListVariable, IProblem<DoubleListVariable>> {

	// ! the probability that a bit is changed -> if null it will be 1 / length
	protected Double mProbability = null;

	// ! range of the results
	protected Range<Double> range = null;

	//! distribution index
	protected double eta_m = 20.0;


	/**
	 * Normal constructor with dynamic probability
	 */
	public RealMutation() {
		super();
	}

	public RealMutation(Range<Double> range) {
		this.range = range;
	}
	
	public RealMutation(Range<Double> range, double eta_m) {
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
	public void mutate(IProblem<DoubleListVariable> problem, MyRandom rand, DoubleListVariable a) {
		
		List<Double> b = a.decode();
		
		if (range == null) range = new Range<Double>(b.size(), Double.MIN_VALUE, Double.MAX_VALUE);
		
		mProbability = 1 / (double) b.size();
		
		for (int j = 0; j < b.size(); j++) {
			if (rand.nextDouble() < mProbability) {
				double u = rand.nextDouble();
				double value = sbxMutation(b.get(j), u, range.getMinimum(j), range.getMaximum(j));
				b.set(j, value);
			} else 
				b.set(j, b.get(j));
		}
		
	}

	
	


}
