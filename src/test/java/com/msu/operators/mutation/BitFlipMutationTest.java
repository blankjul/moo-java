package com.msu.operators.mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.operators.mutation.BitFlipMutation;
import com.msu.util.Random;

public class BitFlipMutationTest {
	
	private class BitFlipMutationMock extends BitFlipMutation {
		public BitFlipMutationMock() {
			super();
		}
		public BitFlipMutationMock(Double probability) {
			super(probability);
		}
		public void mutate_(List<Boolean> l) {
			super.mutate_(l, new Random());
		}
	}
	
	BitFlipMutationMock bitMutation = new BitFlipMutationMock();

	private ArrayList<Boolean> l;
	private ArrayList<Boolean> org;

	@Before
	public void setUp() {
		l = new ArrayList<>(Arrays.asList(false, false, false, true));
		org = new ArrayList<>(l);
	}

	@Test
	public void testFlipAllBits() {
		bitMutation = new BitFlipMutationMock(1.0);
		bitMutation.mutate_(l);
		assertEquals(l, new ArrayList<>(Arrays.asList(true, true, true, false)));
	}
	

	
	@Test
	public void testLargeArrayFlipAtLeastOne() {
		bitMutation = new BitFlipMutationMock(0.2);
		l = new ArrayList<>();
		for (int i = 0; i < 2000; i++) {
			l.add(false);
		}
		org = new ArrayList<>(l);
		bitMutation.mutate_(l);
		assertNotEquals(l, org);
	}
	
	
	

}
