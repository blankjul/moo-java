package com.msu.operators.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IVariable;
import com.msu.util.MyRandom;

public class NoCrossover implements ICrossover<IVariable> {


	@Override
	public List<IVariable> crossover(IVariable a, IVariable b, MyRandom rand) {
		return Arrays.asList(a.copy(), b.copy());
	}

}
