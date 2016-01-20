package com.msu.moo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mock.MockSolution;
import com.msu.moo.model.solution.SolutionDominator;


/**
 * Solution Comparator Test for the minimization case mainly.
 *
 */
public class SolutionComparatorTest {

	
	@Test
	public void testDominationBiObjectiveBoth() {
		MockSolution s1 = MockSolution.create(Arrays.asList(1d,1d));
		MockSolution s2 = MockSolution.create(Arrays.asList(0d,0d));
		assertTrue(SolutionDominator.isDominating(s2, s1));
		assertTrue(SolutionDominator.isDominatedBy(s1, s2));
	}
	
	@Test
	public void testDominationBiObjectiveIndifferent() {
		MockSolution s1 = MockSolution.create(Arrays.asList(0d,1d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,0d));
		assertFalse(SolutionDominator.isDominating(s1, s2));
		assertFalse(SolutionDominator.isDominating(s2, s1));
		assertFalse(SolutionDominator.isDominatedBy(s1, s2));
		assertFalse(SolutionDominator.isDominatedBy(s2, s1));
		assertTrue(SolutionDominator.isIndifferent(s1, s2));
	}
	
	@Test
	public void testConstraintDominationBiObjectiveBoth() {
		MockSolution s1 = MockSolution.create(Arrays.asList(1d,1d), Arrays.asList(0d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,1d), Arrays.asList(1d));
		assertTrue(SolutionDominator.isDominating(s1, s2));
		assertTrue(SolutionDominator.isDominatedBy(s2, s1));
	}
	
	@Test
	public void testConstraintDominationBiObjectiveIndifferent() {
		MockSolution s1 = MockSolution.create(Arrays.asList(0d,1d), Arrays.asList(0d, 5d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,0d), Arrays.asList(5d, 0d));
		assertFalse(SolutionDominator.isDominating(s1, s2));
		assertFalse(SolutionDominator.isDominating(s2, s1));
		assertFalse(SolutionDominator.isDominatedBy(s1, s2));
		assertFalse(SolutionDominator.isDominatedBy(s2, s1));
		assertTrue(SolutionDominator.isIndifferent(s1, s2));
	}
	

	
	

	
	
}
