package com.msu.moo.algorithms.nsgaII;

import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

/**
 * This interface provides general sorting approach which means to
 * get the different fronts of many solutions.
 *
 */
public interface NonDominatedSorting {
	
	public <T extends Solution> List<NonDominatedSolutionSet> run(List<T> solutions);

}
