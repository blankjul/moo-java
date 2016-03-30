package com.msu.moo.util.sorting;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.moo.algorithms.nsgaII.NaiveNonDominatedSorting;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.GenericSolutionSet;
import com.msu.moo.model.solution.NonDominatedSolutionSet;


/**
 * Solution Comparator Test for the minimization case mainly.
 *
 */
public class NaiveNonDominatedSortingTest {


	@Test
	public void testTwoPointsTwoSets() {
		
		MockSolution s1 = MockSolution.create(0, Arrays.asList(1d,1d));
		MockSolution s2 = MockSolution.create(1, Arrays.asList(0d,0d));
		
		
		List<NonDominatedSolutionSet<com.msu.moo.mock.MockVariable>> set = NaiveNonDominatedSorting.sort(new GenericSolutionSet<MockSolution, MockVariable>(Arrays.asList(s1,s2)));
		
		assertEquals(2, set.size());
		assertEquals(s2,set.get(0).getSolutions().get(0));
		assertEquals(s1,set.get(1).getSolutions().get(0));
	}
	


	
	
}
