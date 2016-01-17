package com.msu.moo.algorithms.nsgaII;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.SolutionSet;

public class RankAndCrowdingComparatorTest {

	private RankAndCrowdingComparator<MockVariable> cmp;
	
	private SolutionSet<MockVariable> l = ExampleSolutionSet.get();
	
	@Before
    public void setUp() {
        cmp = new RankAndCrowdingComparator<MockVariable>(new NonDominatedRankIndicator().calculate(l), new CrowdingIndicator().calculate(l));
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
