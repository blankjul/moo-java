package com.msu.moo.algorithms.nsgaII;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.algorithms.impl.nsgaII.RankAndCrowdingComparator;
import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.sorting.indicator.CrowdingDistance;
import com.msu.moo.sorting.indicator.NonDominatedRankIndicator;

public class RankAndCrowdingComparatorTest {
	
	private SolutionSet<MockSolution> set = ExampleSolutionSet.get();
	
	private RankAndCrowdingComparator<MockSolution> cmp = new RankAndCrowdingComparator<>();
	
	
	@Before
    public void setUp() {
		NonDominatedRankIndicator.calculate(set);
		CrowdingDistance.calculate(set);
    }
	
	@Test
	public void testComparatorOnlyRank() {
		int result = cmp.compare(set.get(0), set.get(1));
		assertEquals(1, result);
	}
	
	@Test
	public void testComparatorOnlyRankWorse() {
		int result = cmp.compare(set.get(3), set.get(1));
		assertEquals(-1, result);
	}
	
	@Test
	public void testComparatorSameRankCrowdingDecides() {
		int result = cmp.compare(set.get(1), set.get(2));
		assertEquals(-1, result);
	}
	

}
