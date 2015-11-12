package com.msu.operators.moea;

import java.util.List;

import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.variable.RealVariable;

import com.msu.moo.util.Random;
import com.msu.operators.AbstractMutation;

public class MOEADRealMutation extends AbstractMutation<List<Double>> {

	// ! the probability that a bit is changed -> if null it will be 1 / length
	protected Double mProbability = null;

	// ! range of the results
	protected Double[] range = new Double[] { Double.MIN_VALUE, Double.MAX_VALUE };

	//! distribution index
	protected double eta_m = 20.0;


	public MOEADRealMutation(Double[] range, double eta_m) {
		this.range = range;
		this.eta_m = eta_m;
	}



	@Override
	protected void mutate_(List<Double> b, Random rand) {
		mProbability = 1 / (double) b.size();
		for (int j = 0; j < b.size(); j++) {
			if (rand.nextDouble() < mProbability) {
				RealVariable var = new RealVariable(b.get(j), range[0], range[1] );
				PM.evolve(var, eta_m);
				b.set(j, var.getValue());
			}
		}
	}
	


}
