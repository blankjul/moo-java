package com.msu.moo.util.sorting;

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
	public List<NonDominatedSolutionSet> run(List<Solution> solutions) {
		
		// resulting list
		List<NonDominatedSolutionSet> result = new ArrayList<>();
		
		List<Solution> copy = new ArrayList<Solution>(solutions);
		
		// there are solution to sort
		while (!copy.isEmpty()) {
			
			// empty set and search for all non dominated one
			NonDominatedSolutionSet set = new NonDominatedSolutionSet();
			for(Solution s : copy) {
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
