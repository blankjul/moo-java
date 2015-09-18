package com.msu.moo.indicator;


import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.msu.moo.ExampleSolutionSet;
import com.msu.moo.model.solution.MultiObjectiveSolution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.indicator.NonDominatedRankIndicator;

public class NonDominatedRankIndicatorTest {

	
	@Test
	public void testCalcValuesOfExample() {
		SolutionSet l = ExampleSolutionSet.get();
		Map<MultiObjectiveSolution, Integer> result = new NonDominatedRankIndicator().calculate(l);
		assertEquals(new Integer(0), result.get(l.get(0)) );
		assertEquals(new Integer(1), result.get(l.get(1)));
		assertEquals(new Integer(1), result.get(l.get(2)));
		assertEquals(new Integer(2), result.get(l.get(3)));
		
	}

	
}
