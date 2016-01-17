package com.msu.moo.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mock.MockSolution;

public class SolutionTest {
	
	@Test
	public void testHasConstraintViolationTrue() {
		assertTrue(MockSolution.create(Arrays.asList(), Arrays.asList(0d, 1d)).hasConstrainViolations());
	}
	
	@Test
	public void testHasConstraintViolationFalse() {
		assertFalse(MockSolution.create(Arrays.asList(), Arrays.asList(0d, 0d)).hasConstrainViolations());
	}
	

}
