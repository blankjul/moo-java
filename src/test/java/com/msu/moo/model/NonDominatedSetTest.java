package com.msu.moo.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mocks.MockSolution;

public class NonDominatedSetTest {


	@Test
	public void testAddingSolutionsThatAreDominatedAndNotAdded() {
		NonDominatedSet s = new NonDominatedSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,1d))));
		assertEquals(1, s.size());
	}
	
	@Test
	public void testAddingSolutionsIndifferentAndAdded() {
		NonDominatedSet s = new NonDominatedSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(1d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,1d))));
		assertEquals(2, s.size());
	}
	
	@Test
	public void testAddingSolutionsEqualAndNotAdded() {
		NonDominatedSet s = new NonDominatedSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d))));
		assertEquals(1, s.size());
	}
	
	@Test
	public void testAddingSolutionsThatDominatedCausesRemoving() {
		NonDominatedSet s = new NonDominatedSet();
		s.add(new MockSolution(new ArrayList<Double>(Arrays.asList(1d,0d))));
		MockSolution sol = new MockSolution(new ArrayList<Double>(Arrays.asList(0d,0d)));
		s.add(sol);
		assertEquals(1, s.size());
		assertEquals(sol, s.getSolutions().get(0));
	}
	
	
	

	
}
