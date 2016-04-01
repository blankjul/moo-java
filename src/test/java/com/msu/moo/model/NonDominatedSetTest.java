package com.msu.moo.model;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.NonDominatedSet;

import junit.framework.TestCase;

public class NonDominatedSetTest extends TestCase {

	private NonDominatedSet<ISolution<MockVariable>, MockVariable> s;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		s = new NonDominatedSet<>();
	}

	@Test
	public void testRemoveConstraintViolations() {
		MockSolution s1 = MockSolution.create(Arrays.asList(0d, 0d));
		s.add(s1);
		s.add(MockSolution.create(Arrays.asList(1d, 0d)));
		s.removeSolutionWithConstraintViolations();
		assertEquals(1, s.size());
		assertEquals(s.get(0), s1);
	}
	

	@Test
	public void testAddingSolutionsEqualVariables() {
		s.add(MockSolution.create(Arrays.asList(0d, 0d)));
		s.add(MockSolution.create(Arrays.asList(0d, 0d)));
		assertEquals(1, s.size());
	}

	@Test
	public void testAddingSolutionsThatAreDominatedAndNotAdded() {
		s.add(new MockSolution(Arrays.asList(0d, 0d)));
		s.add(new MockSolution(Arrays.asList(0d, 1d)));
		assertEquals(1, s.size());
	}

	@Test
	public void testAddingSolutionsIndifferentAndAdded() {
		s.add(new MockSolution(Arrays.asList(1d, 0d)));
		s.add(new MockSolution(Arrays.asList(0d, 1d)));
		assertEquals(2, s.size());
	}

	@Test
	public void testAddingSolutionsThatDominatedCausesRemoving() {
		s.add(new MockSolution(Arrays.asList(1d, 0d)));
		MockSolution sol = new MockSolution(Arrays.asList(0d, 0d));
		s.add(sol);
		assertEquals(1, s.size());
		assertEquals(sol, s.getSolutions().get(0));
	}

	@Test
	public void testAddingSolutionsEqualObjective() {
		s.add(MockSolution.create(1, Arrays.asList(1d, 0d)));
		s.add(MockSolution.create(2, Arrays.asList(1d, 0d)));
		s.add(MockSolution.create(2, Arrays.asList(1d, 0d)));
		assertEquals(2, s.size());
	}

}
