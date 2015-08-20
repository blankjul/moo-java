package com.msu.moo.indicator;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class CrowdingRankingIndicatorTest {
	
	
	private SolutionSet l;
	
	@Before
    public void setUp() {
        l = new SolutionSet();
        l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.1,0.1))));
        l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.3 , 0.3))));
        l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.15, 0.25))));
        l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.2, 0.15))));
    }
	
	
	@Test
	public void testCalcValuesOfExample() {
		Map<Solution, Double> result = new CrowdingRankingIndicator().calculate(l);
		assertEquals(Double.POSITIVE_INFINITY, result.get(l.get(0)), 0.01);
		assertEquals(Double.POSITIVE_INFINITY, result.get(l.get(1)), 0.01);
		assertEquals(1.25, result.get(l.get(2)), 0.01);
		assertEquals(1.5, result.get(l.get(3)), 0.01);
		
	}
	//{0.31, 6.10}, {0.22, 7.09}, {0.79, 3.97}, {0.27, 6.93}

}
