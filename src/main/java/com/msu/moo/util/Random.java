package com.msu.moo.util;

import java.util.Collections;
import java.util.List;



public class Random {

	//! the current random object
	protected java.util.Random r;

	public Random() {
		r = new java.util.Random();
	}
	
	public Random(long seed) {
		r = new java.util.Random(seed);
	}

	/**
	 * Create an Integer without range
	 */
	public Integer nextInt(int max) {
		return r.nextInt(max);
	}
	
	/**
	 * Create an Integer in range
	 */
	public Integer nextInt(int min, int max) {
		return r.nextInt(max - min + 1) + min;
	}

	/**
	 * Create a double value between 0 and 1.
	 */
	public Double nextDouble() {
		return r.nextDouble();
	}

	/**
	 * Create a double value in range
	 */
	public Double nextDouble(double min, double max) {
		return min + (max - min) * r.nextDouble();
	}

	
	public void shuffle(List<?> c) {
		Collections.shuffle(c, r);
	}
	
	

}
