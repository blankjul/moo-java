package com.msu.moo.util.sorting;

import java.util.List;

import com.msu.moo.model.NonDominatedSet;
import com.msu.moo.model.Solution;

/**
 * This interface provides general sorting approach which means to
 * get the different fronts of many solutions.
 *
 */
public interface NonDominatedSorting {
	
	List<NonDominatedSet> run(List<Solution> solutions);

}
