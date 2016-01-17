package com.msu.moo.algorithms.nsgaII;


import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.msu.moo.algorithms.nsgaII.NonDominatedRankIndicator;
import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class NonDominatedRankIndicatorTest {

	
	@Test
	public void testCalcValuesOfExample() {
		SolutionSet<MockVariable> l = ExampleSolutionSet.get();
		Map<Solution<MockVariable>, Integer> result = new NonDominatedRankIndicator().calculate(l);
		assertEquals(new Integer(0), result.get(l.get(0)) );
		assertEquals(new Integer(1), result.get(l.get(1)));
		assertEquals(new Integer(1), result.get(l.get(2)));
		assertEquals(new Integer(2), result.get(l.get(3)));
	}

	
}
