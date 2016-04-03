package com.msu.moo.mock;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.solution.SolutionSet;

public class Factory {

	
	public static SolutionSet<MockSolution> create(Double[][] matrix) {
		SolutionSet<MockSolution> set = new SolutionSet<>();
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
