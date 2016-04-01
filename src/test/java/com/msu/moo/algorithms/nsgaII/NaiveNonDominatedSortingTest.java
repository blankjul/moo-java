package com.msu.moo.algorithms.nsgaII;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.moo.algorithms.impl.nsgaII.NaiveNonDominatedSorting;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;


/**
 * Solution Comparator Test for the minimization case mainly.
 *
 */
public class NaiveNonDominatedSortingTest {


	@Test
	public void testTwoPointsTwoSets() {
		
		Solution<MockVariable> s1 = MockSolution.create(0, Arrays.asList(1d,1d));
		Solution<MockVariable> s2 = MockSolution.create(1, Arrays.asList(0d,0d));
		
		List<NonDominatedSet<ISolution<MockVariable>, MockVariable>> set = NaiveNonDominatedSorting.sort(new SolutionSet<>(Arrays.asList(s1,s2)));
		
		assertEquals(2, set.size());
		assertEquals(s2,set.get(0).getSolutions().get(0));
		assertEquals(s1,set.get(1).getSolutions().get(0));
	}
	


	
	
}
