package com.msu.moo.fonseca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.Configuration;
import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class HypervolumeTest {

	
	private Hypervolume hv = new Hypervolume(Configuration.PATH_TO_HYPERVOLUME);
	
	@Test
	public void testHVNoRef() {
		Double d = hv.calculate(MockSolution.create(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}}));
		assertEquals(0.25, d, 0.01);
	}
	
	@Test
	public void testHVWithRefErrorAtCmd() {
		NonDominatedSolutionSet s = MockSolution.create(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}});
		Double d = hv.calculate(s, new ArrayList<Double>(Arrays.asList(1.0,1.0)));
		assertEquals(0.25, d, 0.01);
	}
	
	@Test
	public void testHVWithRefErrorAtCmd2() {
		NonDominatedSolutionSet s = MockSolution.create(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}});
		Double d = hv.calculate(s, new ArrayList<Double>(Arrays.asList(0.0,0.0)));
		assertTrue(d == null);
	}
	
	
	
	
}
