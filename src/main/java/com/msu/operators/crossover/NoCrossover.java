package com.msu.operators.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.util.Random;
import com.msu.moo.util.Util;
import com.msu.operators.AbstractCrossover;

public class NoCrossover<T> extends AbstractCrossover<T> {

	@Override
	protected List<T> crossover_(T a, T b, Random rand) {
		return Arrays.asList(Util.cloneObject(a), Util.cloneObject(b));
	}

}
