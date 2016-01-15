package com.msu.operators.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.util.MyRandom;

public class NoCrossover implements ICrossover<IVariable, IProblem<IVariable>> {


	@Override
	public List<IVariable> crossover(IProblem<IVariable> problem, MyRandom rand, IVariable a, IVariable b) {
		return Arrays.asList(a.copy(), b.copy());
	}

}
