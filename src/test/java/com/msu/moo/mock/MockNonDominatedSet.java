package com.msu.moo.mock;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class MockNonDominatedSet extends NonDominatedSolutionSet<MockVariable>{

	public static NonDominatedSolutionSet<MockVariable> create(Double[][] matrix) {
		NonDominatedSolutionSet<MockVariable> set = new NonDominatedSolutionSet<MockVariable>();
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
