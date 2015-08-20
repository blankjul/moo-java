package com.msu.moo.mocks;

import java.util.List;

import com.msu.moo.model.Solution;
import com.msu.moo.model.interfaces.IVariable;

public class MockSolution extends Solution {

	public MockSolution(List<Double> objectives) {
		super(null, objectives);
	}
	
	public MockSolution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}

}
