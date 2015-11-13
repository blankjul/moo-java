package com.msu.operators.crossover;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.moo.FakeRandom;
import com.msu.interfaces.IVariable;
import com.msu.model.Variable;
import com.msu.operators.crossover.permutation.CycleCrossover;

public class CycleCrossoverTest {

	private class CycleCrossoverMock extends CycleCrossover<Integer> {

		public List<List<Integer>> crossover_(List<Integer> p1, List<Integer> p2, int p) {
			return super.crossover_(p1, p2, p);
		}

	}
	
	CycleCrossoverMock c = new CycleCrossoverMock();
	
	@Test
	public void testCycleCrossover() {
		List<Integer> a = new ArrayList<>(Arrays.asList(8,4,7,3,6,2,5,1,9,0));
		List<Integer> b = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
		List<List<Integer>> result = c.crossover_(a, b, 0);
		assertEquals(new ArrayList<Integer>(Arrays.asList(0,4,7,3,6,2,5,1,8,9)), result.get(0));
		assertEquals(new ArrayList<Integer>(Arrays.asList(8,1,2,3,4,5,6,7,9,0)), result.get(1));
	}

	
	@Test
	public void testCycleCrossoverListOfSizeThree() {
		List<Integer> a = new ArrayList<>(Arrays.asList(1,2,3));
		List<Integer> b = new ArrayList<>(Arrays.asList(3,2,1));
		c.crossover_(a, b, 0);
	}
	
	
	
	@Test
	public void testOfPublication() {
		List<Integer> a = new ArrayList<>(Arrays.asList(6,0,1,2,5,3,4));
		List<Integer> b = new ArrayList<>(Arrays.asList(2,6,3,0,5,4,1));
		
		ArrayList<Object> l = new ArrayList<Object>();
		l.add(2);
		
		List<IVariable> var = new CycleCrossover<>().crossover(new Variable<List<Integer>>(a), new Variable<List<Integer>>(b), new FakeRandom(l));
		@SuppressWarnings("unchecked")
		Variable<List<Integer>> tmp =  (Variable<List<Integer>>)var.get(0);
		assertEquals(Arrays.asList(6,0,3,2,5,4,1), tmp.get());
	
	
	}


}
