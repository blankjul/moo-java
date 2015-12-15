package com.msu.moo.mocks;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.model.variables.Variable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public class MockSolution extends Solution {

	public static int counter = 0;
	
	public MockSolution(List<Double> objectives) {
		super(new Variable<>(counter++), objectives);
	}
	
	public MockSolution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}
	
	
	public static NonDominatedSolutionSet create(Double[][] d) {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
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
