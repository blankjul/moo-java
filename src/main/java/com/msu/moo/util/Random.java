package com.msu.moo.util;

import java.util.Collections;
import java.util.List;

/**
 * This is a random generator which provides some advanced random methods 
 * and uses the SinglePattern to be created.
 *
 */
public class Random {

	//! current seed which is set to the random object
	protected long seed;
	
	//! the current singleton instance
	protected static Random instance;

	//! the current random object
	protected java.util.Random r;

	//! private constructor -> singleton
	private Random() {
		r = new java.util.Random();
	}

	
	/**
	 * Method to create or return the singleton object.
	 * @return
	 */
	public static Random getInstance() {
		if (instance == null) {
			instance = new Random();
		}
		return instance;
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

	/**
	 * Set the seed of the object
	 */
	public void setSeed(long seed) {
		this.seed = seed;
		if (r == null) getInstance();
		r.setSeed(seed);
	}


	/**
	 * Return the seed which was previously set
	 */
	public long getSeed() {
		return seed;
	}
	
	
	public void shuffle(List<?> c) {
		Collections.shuffle(c, r);
	}
	
	

}
