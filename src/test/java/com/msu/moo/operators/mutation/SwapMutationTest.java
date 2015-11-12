package com.msu.moo.operators.mutation;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.msu.interfaces.IVariable;
import com.msu.model.Variable;
import com.msu.moo.util.Random;
import com.msu.operators.mutation.SwapMutation;

public class SwapMutationTest {

	private ArrayList<Integer> l;
	private ArrayList<Integer> org;

	@Before
	public void setUp() {
		l = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
		org = new ArrayList<>(l);
	}

	@Test
	public void testSwapByPositions() {
		IVariable v = new Variable<List<Integer>>(l);
		v = new SwapMutation<>().mutate(v, new Random(123456));
		assertNotEquals(org, v.get());
	}

}
