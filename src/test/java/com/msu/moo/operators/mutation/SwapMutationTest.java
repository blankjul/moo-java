package com.msu.moo.operators.mutation;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.util.Random;

public class SwapMutationTest {
	
	private class SwapMutationMock<T> extends SwapMutation<T> {
		
		public SwapMutationMock() {
			super();
		}
		public void mutate_(List<T> l) {
			super.mutate_(l, new Random());
		}
	}
	
	SwapMutationMock<Integer> swapMutation = new SwapMutationMock<>();

	private ArrayList<Integer> l;
	private ArrayList<Integer> org;

	@Before
	public void setUp() {
		l = new ArrayList<>(Arrays.asList(0,1,2,3,4));
		org = new ArrayList<>(l);
	}

	@Test
	public void testSwapByPositions() {
		for (int i = 0; i < 20; i++) swapMutation.mutate_(l);
		assertNotEquals(l, org);
	}
	
	
	

}
