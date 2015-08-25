package com.msu.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.util.Random;

public class UniformCrossover<T> extends AbstractCrossover<List<T>> {

	
	
	@Override
	protected List<List<T>> crossover_(List<T> a, List<T> b) {
		// copy the both list and change values
		List<T> c1 = new ArrayList<T>();
		List<T> c2 = new ArrayList<T>();

		for (int i = 0; i < a.size(); i++) {
			
			if (Random.getInstance().nextDouble() < 0.5) {
				c1.add(a.get(i));
				c2.add(b.get(i));
			} else {
				c1.add(b.get(i));
				c2.add(a.get(i));
			}
		}
		return new ArrayList<>(Arrays.asList(c1, c2));
	}

}
