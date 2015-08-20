package com.msu.moo.comparator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.comparator.SolutionDominator;
import com.msu.moo.mocks.MockVariable;
import com.msu.moo.model.solution.Solution;


/**
 * Solution Comparator Test for the minimization case mainly.
 *
 */
public class SolutionComparatorTest {

	private SolutionDominator cmp = new SolutionDominator();
	
	@Test
	public void testDominationBiObjectiveBoth() {
		Solution s1 = new Solution(new MockVariable(), new ArrayList<Double>(Arrays.asList(1d,1d)));
		Solution s2 = new Solution(new MockVariable(), new ArrayList<Double>(Arrays.asList(0d,0d)));
		assertTrue(cmp.isDominating(s2, s1));
		assertTrue(cmp.isDominatedBy(s1, s2));
	}
	
	@Test
	public void testDominationBiObjectiveIndifferent() {
		Solution s1 = new Solution(new MockVariable(), new ArrayList<Double>(Arrays.asList(0d,1d)));
		Solution s2 = new Solution(new MockVariable(), new ArrayList<Double>(Arrays.asList(1d,0d)));
		assertFalse(cmp.isDominating(s1, s2));
		assertFalse(cmp.isDominating(s2, s1));
		assertFalse(cmp.isDominatedBy(s1, s2));
		assertFalse(cmp.isDominatedBy(s2, s1));
		assertTrue(cmp.isIndifferent(s1, s2));
	}
	
	
	

	
	
}
