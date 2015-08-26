package com.msu.moo.util.comparator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.ExampleSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.indicator.CrowdingIndicator;
import com.msu.moo.util.indicator.NonDominatedRankIndicator;

public class RankAndCrowdingComparatorTest {

	private RankAndCrowdingComparator cmp;
	
	private SolutionSet l = ExampleSolutionSet.get();
	
	@Before
    public void setUp() {
        cmp = new RankAndCrowdingComparator(new NonDominatedRankIndicator().calculate(l), new CrowdingIndicator().calculate(l));
    }
	
	@Test
	public void testComparatorOnlyRank() {
		int result = cmp.compare(l.get(0), l.get(1));
		assertEquals(1, result);
	}
	
	@Test
	public void testComparatorOnlyRankWorse() {
		int result = cmp.compare(l.get(3), l.get(1));
		assertEquals(-1, result);
	}
	
	@Test
	public void testComparatorSameRankCrowdingDecides() {
		int result = cmp.compare(l.get(1), l.get(2));
		assertEquals(-1, result);
	}
	

}
