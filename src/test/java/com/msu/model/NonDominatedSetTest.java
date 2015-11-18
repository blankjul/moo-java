package com.msu.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.model.variables.Variable;
import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public class NonDominatedSetTest {

	@Test
	public void testRemoveConstraintViolations() {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
		Solution s1 = new Solution(null, null, Arrays.asList(0d,0d));
		s.add(s1);
		s.add(new Solution(null, null, Arrays.asList(1d,0d)));
		s.removeSolutionWithConstraintViolations();
		assertEquals(1, s.size());
		assertEquals(s.get(0), s1);
	}
	
	@Test
	public void testAddingSolutionsEqualObjectives() {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		assertEquals(1, s.size());
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
	
	
	@Test
	public void testAddingSolutionsEqualObjective() {
		NonDominatedSolutionSet s = new NonDominatedSolutionSet();
		s.add(new MockSolution(new Variable<Integer>(1),  new ArrayList<Double>(Arrays.asList(1d,0d))));
		s.add(new MockSolution(new Variable<Integer>(2),  new ArrayList<Double>(Arrays.asList(1d,0d))));
		s.add(new MockSolution(new Variable<Integer>(2),  new ArrayList<Double>(Arrays.asList(1d,0d))));
		assertEquals(2, s.size());
	}
	

	
}
