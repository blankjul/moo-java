package com.msu.moo.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

import com.msu.moo.interfaces.ISelection;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.MyRandom;

public abstract class ASelection<S extends ISolution<V>, V> implements ISelection<S, V>{

	@Override
	public SolutionSet<S,V> next(List<S> population, int n, MyRandom rand) {
		List<S> result = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			result.add(next(population, rand));
		}
		return new SolutionSet<>(result);
	}
	
	
	public S next(List<S> population, MyRandom rand) {
		throw new NotImplementedException("Not Implement how to select one item!");
	}

}
