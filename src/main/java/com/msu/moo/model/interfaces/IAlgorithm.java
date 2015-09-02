package com.msu.moo.model.interfaces;

import com.msu.moo.model.solution.NonDominatedSolutionSet;

public interface IAlgorithm<P extends IProblem> {

	/**
	 * Execute the algorithm.
	 * 
	 * @param problem
	 *            instance of the problem
	 * @return NonDomintedSet which was calculated
	 */
	public NonDominatedSolutionSet run(P problem);


	/**
	 * @return maximal number of evaluations that should be allowed
	 */
	public void setMaxEvaluations(long n);

	/**
	 * @return the name of this algorithm or instance with different parameters
	 */
	public String getName();
	
	/**
	 * @param name for this algorithm
	 */
	public void setName(String name);

}
