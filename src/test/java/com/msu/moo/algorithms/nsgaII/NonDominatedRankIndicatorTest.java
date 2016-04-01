package com.msu.moo.algorithms.nsgaII;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;
import com.msu.moo.algorithms.impl.nsgaII.NonDominatedRankIndicator;
import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.SolutionSet;

public class NonDominatedRankIndicatorTest {

	
	@Test
	public void testCalcValuesOfExample() {
		SolutionSet<NSGAIISolution<MockVariable>, MockVariable> l = ExampleSolutionSet.getAsNSGAIISolution();
		NonDominatedRankIndicator.calculate(l);
		assertEquals(new Integer(0), l.get(0).getRank());
		assertEquals(new Integer(1), l.get(1).getRank());
		assertEquals(new Integer(1), l.get(2).getRank());
		assertEquals(new Integer(2), l.get(3).getRank());
	}

	
}
