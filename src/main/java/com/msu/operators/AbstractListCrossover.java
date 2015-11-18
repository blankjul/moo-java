package com.msu.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.util.Random;

public abstract class AbstractListCrossover<T> extends AbstractCrossover<List<T>> {

	@Override
	protected List<List<T>> crossover_(List<T> a, List<T> b, IProblem problem, Random rand) {

		if (a.size() != b.size())
			throw new RuntimeException("List crossover is not possible when the size of the lists are different!");

		List<List<T>> result = new ArrayList<>();

		if (a.isEmpty()) {
			result.add(new ArrayList<T>());
			result.add(new ArrayList<T>());
		} else if (a.size() == 1) {
			result.add(new ArrayList<T>(a));
			result.add(new ArrayList<T>(b));
		} else if (a.size() == 2) {
			result.add(new ArrayList<T>(Arrays.asList(a.get(0), b.get(1))));
			result.add(new ArrayList<T>(Arrays.asList(a.get(1), b.get(0))));
		} else {
			result = crossoverLists(a, b, rand);
		}

		return result;
	}

	abstract protected List<List<T>> crossoverLists(List<T> a, List<T> b, Random rand);

}
