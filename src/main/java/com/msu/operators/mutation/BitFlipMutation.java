package com.msu.operators.mutation;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.util.MyRandom;

/**
 * This is a basic BitFlipMutation which is allowed on all List<Boolean> objects.
 * There are two objects: there could be a fix probability or it is 1/length of object.
 * 
 * [0,0,1,0] ----> [1,0,1,0]
 *
 */
public class BitFlipMutation extends AbstractMutation<List<Boolean>> {

	//! the probability that a bit is changed
	protected Double probability = null;
	

	/**
	 * Normal constructor with dynamic probability 
	 */
	public BitFlipMutation() {
		super();
	}

	/**
	 * Constructor which fixes the probability for all inputs.
	 * @param probability that a bit is changed
	 */
	public BitFlipMutation(Double probability) {
		super();
		this.probability = probability;
	}


	@Override
	public List<Boolean> mutate_(List<Boolean> var, IProblem problem, MyRandom rand, IEvaluator evaluator) {
		List<Boolean> result = new ArrayList<>();
		if (probability == null) probability = 1 / (double) var.size();
		for (int i = 0; i < var.size(); i++) {
			if (rand.nextDouble() < probability) result.add(!var.get(i));
			else result.add(var.get(i));
		}
		return result;
	}



}
