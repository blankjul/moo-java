package com.msu.moo.model.interfaces;

import com.msu.moo.model.solution.NonDominatedSolutionSet;

public interface IAlgorithm<V extends IVariable<?>, P extends IProblem<V, P>> {
	
	
	/**
	 * Execute the algorithm.
	 * @param problem instance of the problem
	 * @return NonDomintedSet which was calculated
	 */
	public NonDominatedSolutionSet run(P problem);



}
