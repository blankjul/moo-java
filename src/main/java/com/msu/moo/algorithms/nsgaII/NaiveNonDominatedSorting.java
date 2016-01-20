package com.msu.moo.algorithms.nsgaII;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

/**
 * Implementation of a naive approach which searching for the current front off
 * all individuals. Then the front is removed from the set and the step is repeated until
 * there are no elements.
 *
 */
public class NaiveNonDominatedSorting implements NonDominatedSorting {


	@Override
	public <T> List<NonDominatedSolutionSet<T>> run(List<Solution<T>> solutions) {
		
		// resulting list
		List<NonDominatedSolutionSet<T>> result = new ArrayList<>();
		
		List<Solution<T>> copy = new ArrayList<Solution<T>>(solutions);
		
		// there are solution to sort
		while (!copy.isEmpty()) {
			
			// empty set and search for all non dominated one
			NonDominatedSolutionSet<T> set = new NonDominatedSolutionSet<T>();
			
			for(Solution<T> s : copy) {
				set.add(s);
			}
			
			// add all non dominated solution to list
			result.add(set);
			// remove all solutions from the copy object
			copy.removeAll(set.getSolutions());
			
		}

		
		return result;
	}

	


	

}
