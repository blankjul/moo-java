package com.msu.moo.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mock.MockSolution;
import com.msu.moo.mock.MockSolutionSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.SolutionSet;

import junit.framework.TestCase;

public class SolutionSetTest extends TestCase{

	private SolutionSet<MockSolution, MockVariable> s;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		s = new MockSolutionSet();
	}
	

	@Test
	public void testGetVector() {
		s.add(MockSolution.create(Arrays.asList(0d,0d)));
		s.add(MockSolution.create(Arrays.asList(0d,1d)));
		assertEquals(new ArrayList<Double>(Arrays.asList(0d,0d)), s.getVector(0));
	}
	

	


	
}
