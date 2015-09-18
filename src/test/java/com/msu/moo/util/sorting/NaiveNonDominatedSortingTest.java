package com.msu.moo.util.sorting;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.moo.mocks.MockVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.MultiObjectiveSolution;


/**
 * Solution Comparator Test for the minimization case mainly.
 *
 */
public class NaiveNonDominatedSortingTest {


	@Test
	public void testTwoPointsTwoSets() {
		MultiObjectiveSolution s1 = new MultiObjectiveSolution(new MockVariable(), new ArrayList<Double>(Arrays.asList(1d,1d)));
		MultiObjectiveSolution s2 = new MultiObjectiveSolution(new MockVariable(), new ArrayList<Double>(Arrays.asList(0d,0d)));
		
		List<NonDominatedSolutionSet> set = new NaiveNonDominatedSorting().run(new ArrayList<>(Arrays.asList(s1,s2)));
		
		assertEquals(2, set.size());
		assertEquals(s2,set.get(0).getSolutions().get(0));
		assertEquals(s1,set.get(1).getSolutions().get(0));
	}
	


	
	
}
