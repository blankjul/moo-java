package com.msu.moo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mock.MockSolution;
import com.msu.moo.model.solution.SolutionDominator;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;


/**
 * Solution Comparator Test for the minimization case mainly.
 *
 */
public class SolutionComparatorWithConstraintsTest {

	private SolutionDominator cmp = new SolutionDominatorWithConstraints();
	
	@Test
	public void testDominationBiObjectiveBoth() {
		MockSolution s1 = MockSolution.create(Arrays.asList(1d,1d), Arrays.asList(0d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,1d), Arrays.asList(1d));
		assertTrue(cmp.isDominating(s1, s2));
		assertTrue(cmp.isDominatedBy(s2, s1));
	}
	
	@Test
	public void testDominationBiObjectiveIndifferent() {
		MockSolution s1 = MockSolution.create(Arrays.asList(0d,1d), Arrays.asList(0d, 5d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,0d), Arrays.asList(5d, 0d));
		assertFalse(cmp.isDominating(s1, s2));
		assertFalse(cmp.isDominating(s2, s1));
		assertFalse(cmp.isDominatedBy(s1, s2));
		assertFalse(cmp.isDominatedBy(s2, s1));
		assertTrue(cmp.isIndifferent(s1, s2));
	}
	

	
	
}
