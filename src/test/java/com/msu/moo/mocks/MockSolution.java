package com.msu.moo.mocks;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class MockSolution extends Solution {

	public MockSolution(List<Double> objectives) {
		super(null, objectives);
	}
	
	public MockSolution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}
	
	
	public static SolutionSet create(Double[][] d) {
		SolutionSet s = new SolutionSet();
		for (int i = 0; i < d.length; i++) {
			List<Double> l = new ArrayList<>();
			for (int j = 0; j < d[i].length; j++) {
				l.add(d[i][j]);
			}
			s.add(new MockSolution(l));
		}
		return s;
		
	}

}
