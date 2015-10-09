package com.msu.moo.operators.crossover;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.util.Util;

public class NoCrossover<T> extends AbstractCrossover<T> {

	@Override
	protected List<T> crossover_(T a, T b) {
		return Arrays.asList(Util.cloneObject(a), Util.cloneObject(b));
	}

}
