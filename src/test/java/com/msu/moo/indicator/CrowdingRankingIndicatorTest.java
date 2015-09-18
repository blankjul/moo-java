package com.msu.moo.indicator;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import com.msu.moo.ExampleSolutionSet;
import com.msu.moo.model.solution.MultiObjectiveSolution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.indicator.CrowdingIndicator;

public class CrowdingRankingIndicatorTest {
	
	
	@Test
	public void testCalcValuesOfExample() {
		SolutionSet l = ExampleSolutionSet.get();
		Map<MultiObjectiveSolution, Double> result = new CrowdingIndicator().calculate(l);
		assertEquals(Double.POSITIVE_INFINITY, result.get(l.get(0)), 0.01);
		assertEquals(1.25, result.get(l.get(1)), 0.01);
		assertEquals(1.5, result.get(l.get(2)), 0.01);
		assertEquals(Double.POSITIVE_INFINITY, result.get(l.get(3)), 0.01);
		
	}

}
