package com.msu.operators.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractCrossover;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class NoCrossover<T> extends AbstractCrossover<T> {

	@Override
	public List<T> crossover_(T a, T b, IProblem problem, MyRandom rand, IEvaluator evaluator) {
		return Arrays.asList(Util.cloneObject(a), Util.cloneObject(b));
	}

}
