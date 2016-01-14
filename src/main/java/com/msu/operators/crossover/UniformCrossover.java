package com.msu.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.operators.AbstractListCrossover;

public class UniformCrossover<T> extends AbstractListCrossover<T> {

	
	
	@Override
	protected List<List<T>> crossoverLists(List<T> a, List<T> b) {
		// copy the both list and change values
		List<T> c1 = new ArrayList<T>();
		List<T> c2 = new ArrayList<T>();

		for (int i = 0; i < a.size(); i++) {
			
			if (rand.nextDouble() < 0.5) {
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
