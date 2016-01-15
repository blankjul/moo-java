package com.msu.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.MockSolution;
import com.msu.MockVariable;
import com.msu.moo.model.solution.SolutionSet;

import junit.framework.TestCase;

public class SolutionSetTest extends TestCase{

	private SolutionSet<MockVariable> s;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		s = new SolutionSet<>();
	}
	

	@Test
	public void testGetVector() {
		s.add(MockSolution.create(Arrays.asList(0d,0d)));
		s.add(MockSolution.create(Arrays.asList(0d,1d)));
		assertEquals(new ArrayList<Double>(Arrays.asList(0d,0d)), s.getVector(0));
	}
	

	@Test
	public void testGetNormalizedFront() {
		s.add(MockSolution.create(Arrays.asList(1d,1d)));
		s.add(MockSolution.create(Arrays.asList(2d,2d)));
		s.add(MockSolution.create(Arrays.asList(3d,3d)));
		
		SolutionSet<MockVariable> n = s.normalize();
		
		assertEquals(Arrays.asList(0d,0d), n.get(0).getObjectives());
		assertEquals(Arrays.asList(0.5d,0.5d), n.get(1).getObjectives());
		assertEquals(Arrays.asList(1d,1d), n.get(2).getObjectives());
	}
	
	@Test
	public void testGetNormalizedFrontWithNegativeValue() {
		s.add(MockSolution.create(Arrays.asList(-1d,-1d)));
		s.add(MockSolution.create(Arrays.asList(0d,0d)));
		s.add(MockSolution.create(Arrays.asList(1d,1d)));
		
		SolutionSet<MockVariable> n = s.normalize();
		
		assertEquals(new ArrayList<Double>(Arrays.asList(0d,0d)), n.get(0).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(0.5d,0.5d)), n.get(1).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(1d,1d)), n.get(2).getObjectives());
	}
	
	@Test
	public void testInversionOfSolutionSet() {
		
		s.add(MockSolution.create(Arrays.asList(0d,0d)));
		s.add(MockSolution.create(Arrays.asList(1d,1d)));
	
		SolutionSet<MockVariable> n = s.invert(5.0);
		
		assertEquals(new ArrayList<Double>(Arrays.asList(5d,5d)), n.get(0).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(4d,4d)), n.get(1).getObjectives());
	}



	
}
