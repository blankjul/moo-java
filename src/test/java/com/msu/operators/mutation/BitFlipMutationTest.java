package com.msu.operators.mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.model.variables.ListVariable;
import com.msu.util.MyRandom;

public class BitFlipMutationTest {
	
	
	BitFlipMutation bitMutation = new BitFlipMutation();

	private ListVariable<Boolean> l;
	private MyRandom rand = new MyRandom(123456);

	@Before
	public void setUp() {
		l = new ListVariable<Boolean>(Arrays.asList(false, false, false, true));
	}

	@Test
	public void testFlipAllBits() {
		bitMutation = new BitFlipMutation(1.0);
		@SuppressWarnings("unchecked")
		ListVariable<Boolean> result = (ListVariable<Boolean>) bitMutation.mutate(l, null, rand);
		assertEquals(result.get(), new ArrayList<>(Arrays.asList(true, true, true, false)));
	}
	

	@Test
	public void testLargeArrayFlipAtLeastOne() {
		bitMutation = new BitFlipMutation(0.2);
		List<Boolean> list = new ArrayList<>();
		for (int i = 0; i < 2000; i++) {
			list.add(false);
		}
		@SuppressWarnings("unchecked")
		ListVariable<Boolean> result = (ListVariable<Boolean>) bitMutation.mutate(new ListVariable<>(list), null, rand);
		assertNotEquals(l.get(), result.get());
	}
	
	
	

}
