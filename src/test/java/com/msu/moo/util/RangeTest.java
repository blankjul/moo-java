package com.msu.moo.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class RangeTest {
	
	
	@Test
	public void testRange1() {
		Range<Double> r = new Range<>();
		r.add(new ArrayList<Double>(Arrays.asList(1.0,0.0)));
		r.add(new ArrayList<Double>(Arrays.asList(2.0,6.0)));
		assertEquals(1.0, r.ranges.get(0).first, 0.01);
		assertEquals(2.0, r.ranges.get(0).second, 0.01);
		
		assertEquals(0.0, r.ranges.get(1).first, 0.01);
		assertEquals(6.0, r.ranges.get(1).second, 0.01);
	}

}
