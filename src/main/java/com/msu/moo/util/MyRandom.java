package com.msu.moo.util;

import java.util.Collections;
import java.util.List;

public class MyRandom {

	// ! the current random object
	protected java.util.Random r;

	public MyRandom() {
		r = new java.util.Random();
	}

	public MyRandom(long seed) {
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
		return r.ints(min, max).findFirst().getAsInt();
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

	public <T> T select(List<T> l) {
		return l.get(r.nextInt(l.size()));
	}

}
