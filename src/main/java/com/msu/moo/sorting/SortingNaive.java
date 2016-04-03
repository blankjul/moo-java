package com.msu.moo.sorting;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;

/**
 * Implementation of a naive approach which searching for the current front off
 * all individuals. Then the front is removed from the set and the step is repeated until
 * there are no elements.
 *
 */
public class SortingNaive  {


	public static <S extends ISolution<?>> List<NonDominatedSet<S>> sort(SolutionSet<S> solutions) {
		
		// resulting list
		List<NonDominatedSet<S>> result = new ArrayList<>();
		
		SolutionSet<S> copy = new SolutionSet<S>(solutions);
		
		// there are solution to sort
		while (!copy.isEmpty()) {
			
			// empty set and search for all non dominated one
			NonDominatedSet<S> set = new NonDominatedSet<>(copy);
			
			for(S s : copy) {
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
