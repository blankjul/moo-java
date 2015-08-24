package com.msu.moo.problems;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.problems.knp.Item;
import com.msu.moo.problems.knp.KnapsackProblem;
import com.msu.moo.problems.knp.KnapsackVariable;

public class KnapsackTest {

	private KnapsackProblem k;
	private ArrayList<Item> l;

	@Before
	public void setUp() {
		l = new ArrayList<Item>(Arrays.asList(new Item(1, 1), new Item(2, 2), new Item(3, 3)));
		k = new KnapsackProblem(3, l);
	}

	@Test
	public void testGetWeightFunction() {
		assertEquals(6, (int) KnapsackProblem.getWeight(l, new ArrayList<Boolean>(Arrays.asList(true, true, true))));
	}

	@Test
	public void testEvaluateFunctionIsNonZero() {
		Solution s = k.evaluate(new KnapsackVariable(new ArrayList<Boolean>(Arrays.asList(true, true, false))));
		assertEquals(3d, s.getObjectives().get(0), 0.05);
	}

	@Test
	public void testEvaluateFunctionIsZero() {
		Solution s = k.evaluate(new KnapsackVariable(new ArrayList<Boolean>(Arrays.asList(true, true, true))));
		assertEquals(0d, s.getObjectives().get(0), 0.05);
	}

	@Test (expected=RuntimeException.class) 
	public void testWrongSizeOfTour() throws RuntimeException {
		k.evaluate(new KnapsackVariable(new ArrayList<Boolean>(Arrays.asList(true, true))));
	}
	
	

}
