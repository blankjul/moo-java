package com.msu.moo.fonseca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;
import com.msu.moo.mock.MockNonDominatedSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.NonDominatedSet;

public class HypervolumeTest {

	
	
	@Test
	public void testHVNoRef() {
		Double d = Hypervolume.calculate(MockNonDominatedSet.createAsNSGADominatedSet(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}}).getSolutions());
		assertEquals(0.25, d, 0.01);
	}
	
	@Test
	public void testHVWithRefErrorAtCmd() {
		NonDominatedSet<NSGAIISolution<MockVariable>, MockVariable> s = MockNonDominatedSet.createAsNSGADominatedSet(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}});
		Double d = Hypervolume.calculate(s.getSolutions(), new ArrayList<Double>(Arrays.asList(1.0,1.0)));
		assertEquals(0.25, d, 0.01);
	}
	
	@Test
	public void testHVWithRefErrorAtCmd2() {
		NonDominatedSet<NSGAIISolution<MockVariable>, MockVariable> s = MockNonDominatedSet.createAsNSGADominatedSet(new Double[][] {{1.0,0.0}, {0.5,0.5}, {0.0,1.0}});
		Double d = Hypervolume.calculate(s.getSolutions(), new ArrayList<Double>(Arrays.asList(0.0,0.0)));
		assertTrue(d == null);
	}
	
	
	
	
}
