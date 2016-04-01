package com.msu.moo.mock;

import java.util.Collection;

import com.msu.moo.model.solution.SolutionSet;

public class MockSolutionSet extends SolutionSet<MockSolution, MockVariable>{

	public MockSolutionSet() {
		super();
	}

	public MockSolutionSet(Collection<MockSolution> c) {
		super(c);
	}

	public MockSolutionSet(int initialCapacity) {
		super(initialCapacity);
	}
	
	
	
	
}
