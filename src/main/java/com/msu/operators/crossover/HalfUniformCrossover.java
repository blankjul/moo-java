package com.msu.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.operators.AbstractListCrossover;
import com.msu.util.MyRandom;

public class HalfUniformCrossover<T> extends AbstractListCrossover<T> {

	/**
	 * Returns all the positions that are different at the two parents.
	 * 
	 * @return all indices that are different!
	 */
	protected List<Integer> getNotEqualIndices(List<T> a, List<T> b) {
		List<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i)))
				l.add(i);
		}
		return l;
	}

	@Override
	protected List<List<T>> crossoverLists(List<T> a, List<T> b, MyRandom rand) {
		// copy the both list and change values
		List<T> c1 = new ArrayList<T>(a);
		List<T> c2 = new ArrayList<T>(b);

		// get not equal indices
		List<Integer> indices = getNotEqualIndices(a, b);
		int hammiltonDistance = indices.size();

		// shuffle to ensure randomness
		rand.shuffle(indices);

		// swap half of the not equal positions 
		// (for loop since shuffled before!)
		for (int i = 0; i < hammiltonDistance / 2.0; i++) {
			int idx = indices.get(i);
			c1.set(idx, b.get(idx));
			c2.set(idx, a.get(idx));
		}

		return new ArrayList<>(Arrays.asList(c1, c2));
	}

}
