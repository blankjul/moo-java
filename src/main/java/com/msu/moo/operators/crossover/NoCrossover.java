package com.msu.moo.operators.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.MyRandom;

public class NoCrossover implements ICrossover<IVariable> {


	@Override
	public List<IVariable> crossover(IVariable a, IVariable b, MyRandom rand) {
		return Arrays.asList(a.copy(), b.copy());
	}

}
