package com.msu.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.msu.MockSolution;
import com.msu.moo.model.solution.SolutionDominator;


/**
 * Solution Comparator Test for the minimization case mainly.
 *
 */
public class SolutionComparatorTest {

	private SolutionDominator cmp = new SolutionDominator();
	
	@Test
	public void testDominationBiObjectiveBoth() {
		MockSolution s1 = MockSolution.create(Arrays.asList(1d,1d));
		MockSolution s2 = MockSolution.create(Arrays.asList(0d,0d));
		assertTrue(cmp.isDominating(s2, s1));
		assertTrue(cmp.isDominatedBy(s1, s2));
	}
	
	@Test
	public void testDominationBiObjectiveIndifferent() {
		MockSolution s1 = MockSolution.create(Arrays.asList(0d,1d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,0d));
		assertFalse(cmp.isDominating(s1, s2));
		assertFalse(cmp.isDominating(s2, s1));
		assertFalse(cmp.isDominatedBy(s1, s2));
		assertFalse(cmp.isDominatedBy(s2, s1));
		assertTrue(cmp.isIndifferent(s1, s2));
	}
	
	
	

	
	
}
