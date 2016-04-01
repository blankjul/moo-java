package com.msu.moo.algorithms.nsgaII;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.algorithms.impl.nsgaII.CrowdingDistance;
import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;
import com.msu.moo.algorithms.impl.nsgaII.NonDominatedRankIndicator;
import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.SolutionSet;

public class RankAndCrowdingComparatorTest {

	private Comparator<NSGAIISolution<MockVariable>> cmp;
	
	private SolutionSet<NSGAIISolution<MockVariable>, MockVariable> set = ExampleSolutionSet.getAsNSGAIISolution();
	
	@Before
    public void setUp() {
		cmp = Comparator.comparingInt(NSGAIISolution::getRank);
		cmp = cmp.reversed();
		cmp = cmp.thenComparingDouble(NSGAIISolution::getCrowding);
		
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
