package com.msu.operators.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.operators.AbstractCrossover;

public class NoCrossover extends AbstractCrossover<IVariable> {

	@Override
	public List<IVariable> crossover_(IVariable a, IVariable b) {
		return Arrays.asList(a.copy(), b.copy());
	}

}
