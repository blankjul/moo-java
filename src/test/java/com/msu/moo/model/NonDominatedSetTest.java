package com.msu.moo.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class NonDominatedSetTest {

	@Test
	public void testAddingSolutionsEqualObjectives() {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		assertEquals(2, s.size());
	}
	

	@Test
	public void testAddingSolutionsThatAreDominatedAndNotAdded() {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,1d))));
		assertEquals(1, s.size());
	}
	
	@Test
	public void testAddingSolutionsIndifferentAndAdded() {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(1d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,1d))));
		assertEquals(2, s.size());
	}
	

	
	@Test
	public void testAddingSolutionsThatDominatedCausesRemoving() {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(1d,0d))));
		MockSolution sol = new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d)));
		s.add(sol);
		assertEquals(1, s.size());
		assertEquals(sol, s.getSolutions().get(0));
	}
	
	
	

	
}
