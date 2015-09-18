package com.msu.moo.util.sorting;

import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.MultiObjectiveSolution;

/**
 * This interface provides general sorting approach which means to
 * get the different fronts of many solutions.
 *
 */
public interface NonDominatedSorting {
	
	List<NonDominatedSolutionSet> run(List<MultiObjectiveSolution> solutions);

}
