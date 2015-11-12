package com.msu.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.msu.model.Variable;
import com.msu.moo.mocks.MockVariable;
import com.msu.moo.model.solution.Solution;
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
		Solution s1 = new Solution(new Variable<Object>(null), Arrays.asList(1d,1d), Arrays.asList(0d));
		Solution s2 = new Solution(new Variable<Object>(null), Arrays.asList(1d,1d), Arrays.asList(1d));
		assertTrue(cmp.isDominating(s1, s2));
		assertTrue(cmp.isDominatedBy(s2, s1));
	}
	
	@Test
	public void testDominationBiObjectiveIndifferent() {
		Solution s1 = new Solution(new MockVariable(), Arrays.asList(0d,1d), Arrays.asList(0d, 5d));
		Solution s2 = new Solution(new MockVariable(), Arrays.asList(1d,0d), Arrays.asList(5d, 0d));
		assertFalse(cmp.isDominating(s1, s2));
		assertFalse(cmp.isDominating(s2, s1));
		assertFalse(cmp.isDominatedBy(s1, s2));
		assertFalse(cmp.isDominatedBy(s2, s1));
		assertTrue(cmp.isIndifferent(s1, s2));
	}
	

	
	
}
