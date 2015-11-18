package com.msu.moo.algorithms.nsgaII;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.moo.ExampleSolutionSet;
import com.msu.model.variables.Variable;
import com.msu.moo.algorithms.nsgaII.CrowdingIndicator;
import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class CrowdingRankingIndicatorTest {
	
	
	@Test
	public void testCalcValuesOfExample() {
		SolutionSet l = ExampleSolutionSet.get();
		Map<Solution, Double> result = new CrowdingIndicator().calculate(l);
		assertEquals(Double.POSITIVE_INFINITY, result.get(l.get(0)), 0.01);
		assertEquals(1.25, result.get(l.get(1)), 0.01);
		assertEquals(1.5, result.get(l.get(2)), 0.01);
		assertEquals(Double.POSITIVE_INFINITY, result.get(l.get(3)), 0.01);
	}
	
	
	@Test
	public void testCrowdingSamePoint() {
		SolutionSet l = new SolutionSet();
		l.add(new MockSolution(new Variable<Integer>(0), new ArrayList<Double>(Arrays.asList(0.0, 0.0))));
		l.add(new MockSolution(new Variable<Integer>(1), new ArrayList<Double>(Arrays.asList(1.0, 1.0))));
		l.add(new MockSolution(new Variable<Integer>(1), new ArrayList<Double>(Arrays.asList(1.0, 1.0))));
		l.add(new MockSolution(new Variable<Integer>(1), new ArrayList<Double>(Arrays.asList(1.0, 1.0))));
		l.add(new MockSolution(new Variable<Integer>(2), new ArrayList<Double>(Arrays.asList(2.0, 2.0))));
		Map<Solution, Double> result = new CrowdingIndicator().calculate(l);
		assertEquals(0, result.get(l.get(2)), 0.01);
	}

	

	
}
