package com.msu.moo.operators.crossover;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.msu.operators.crossover.HalfUniformCrossover;

public class HalfUniformCrossoverTest {

	private class HalfUniformCrossoverMock extends HalfUniformCrossover<Boolean> {
		public int calcHammiltonDistance_(List<Boolean> a, List<Boolean> b) {
			return getNotEqualIndices(a, b).size();
		}

	}
	
	HalfUniformCrossoverMock c = new HalfUniformCrossoverMock();
	
	@Test
	public void testCalculateHammiltonDistance() {
		int result = c.calcHammiltonDistance_(new ArrayList<Boolean>(Arrays.asList(true, false, true)), 
				new ArrayList<Boolean>(Arrays.asList(false, true, true)));
		assertEquals(2, result);
	}


}
