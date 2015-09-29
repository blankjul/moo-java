package com.msu.moo;

/**
 * This is the configuration of the moo-java framework. To provide some extra
 * feature which are implemented in other programming language, interfaces are
 * used.
 * 
 * -----------------------------------------------------------------------------
 * EAF from Fonseca
 * -----------------------------------------------------------------------------
 * The empirical first-order attainment function (EAF) is used to assess the
 * performance of stochastic multiobjective optimisers such as multiobjective
 * evolutionary algorithms
 * 
 * https://eden.dei.uc.pt/~cmfonsec/software.html
 * 
 * 
 * -----------------------------------------------------------------------------
 * Computation of the Hypervolume from Fonseca
 * -----------------------------------------------------------------------------
 * This program implements a recursive, dimension-sweep algorithm for computing
 * the hypervolume indicator of the quality of a set of n non-dominated points
 * in d dimensions
 * 
 * http://iridia.ulb.ac.be/~manuel/hypervolume
 *
 * -----------------------------------------------------------------------------
 *
 */
public class Configuration {

	// ! path to Fonseca's EAF C Implementation
	public static String PATH_TO_EAF = "vendor/aft-0.95/eaf";

	// ! path to Fonseca's Hypervolume C Implementation
	public static String PATH_TO_HYPERVOLUME = "vendor/hv-1.3-src/hv";
	

}







