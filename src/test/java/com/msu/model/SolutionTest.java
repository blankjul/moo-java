package com.msu.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.model.solution.Solution;

public class SolutionTest {
	
	
	@Test
	public void testHasConstraintViolationTrue() {
		assertTrue(new Solution(null, null, Arrays.asList(0d, 1d)).hasConstrainViolations());
	}
	
	@Test
	public void testHasConstraintViolationFalse() {
		assertFalse(new Solution(null, null, Arrays.asList(0d, 0d)).hasConstrainViolations());
	}

}
