package com.msu.moo.algorithms.nsgaII;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockVariable;

public class RankAndCrowdingComparatorTest {

	private RankAndCrowdingComparator<MockVariable> cmp = new RankAndCrowdingComparator<>();
	
	private NSGAIISolutionSet<MockVariable> set = ExampleSolutionSet.get();
	
	@Before
    public void setUp() {
		NonDominatedRankIndicator.calculate(set);
		CrowdingIndicator.calculate(set);
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
