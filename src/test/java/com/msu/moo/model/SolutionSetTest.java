package com.msu.moo.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.SolutionSet;

public class SolutionSetTest {


	@Test
	public void testGetVector() {
		SolutionSet s = new SolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,1d))));
		assertEquals(new ArrayList<Double>(Arrays.asList(0d,0d)), s.getVector(0));
	}
	

	@Test
	public void testGetNormalizedFront() {
		SolutionSet s = new SolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(1d,1d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(2d,2d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(3d,3d))));
		SolutionSet n = s.normalize();
		assertEquals(new ArrayList<Double>(Arrays.asList(0d,0d)), n.get(0).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(0.5d,0.5d)), n.get(1).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(1d,1d)), n.get(2).getObjectives());
	}
	
	@Test
	public void testGetNormalizedFrontWithNegativeValue() {
		SolutionSet s = new SolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(-1d,-1d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(1d,1d))));
		SolutionSet n = s.normalize();
		assertEquals(new ArrayList<Double>(Arrays.asList(0d,0d)), n.get(0).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(0.5d,0.5d)), n.get(1).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(1d,1d)), n.get(2).getObjectives());
	}
	
	@Test
	public void testInversionOfSolutionSet() {
		SolutionSet s = new SolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(1d,1d))));
		SolutionSet n = s.invert(5.0);
		assertEquals(new ArrayList<Double>(Arrays.asList(5d,5d)), n.get(0).getObjectives());
		assertEquals(new ArrayList<Double>(Arrays.asList(4d,4d)), n.get(1).getObjectives());
	}



	
}
