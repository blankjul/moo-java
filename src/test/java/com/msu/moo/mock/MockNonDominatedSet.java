package com.msu.moo.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;
import com.msu.moo.model.solution.NonDominatedSet;

public class MockNonDominatedSet extends NonDominatedSet<MockSolution, MockVariable>{

	
	
	
	
	public MockNonDominatedSet() {
		super();
	}

	public MockNonDominatedSet(Collection<? extends MockSolution> set) {
		super(set);
	}

	public static MockNonDominatedSet create(Double[][] matrix) {
		MockNonDominatedSet set = new MockNonDominatedSet();
		for (Double[] row : matrix) {
			List<Double> l = new ArrayList<>();
			for (Double d : row) {
				l.add(d);
			}
			set.add(MockSolution.create(l));
		}
		return set;
	}
	
	
	public static NonDominatedSet<NSGAIISolution<MockVariable>, MockVariable> createAsNSGADominatedSet(Double[][] matrix) {
		NonDominatedSet<NSGAIISolution<MockVariable>, MockVariable> set = new NonDominatedSet<>();
		for (Double[] row : matrix) {
			List<Double> l = new ArrayList<>();
			for (Double d : row) {
				l.add(d);
			}
			set.add(MockSolution.create(l));
		}
		return set;
	}
	
	
}
