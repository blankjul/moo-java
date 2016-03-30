package com.msu.moo.algorithms.nsgaII;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.GenericSolutionSet;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

/**
 * Implementation of a naive approach which searching for the current front off
 * all individuals. Then the front is removed from the set and the step is repeated until
 * there are no elements.
 *
 */
public class NaiveNonDominatedSorting  {


	public static <S extends Solution<V>,V> List<NonDominatedSolutionSet<V>> sort(GenericSolutionSet<S, V> solutions) {
		
		// resulting list
		List<NonDominatedSolutionSet<V>> result = new ArrayList<>();
		
		List<Solution<V>> copy = new ArrayList<Solution<V>>(solutions);
		
		// there are solution to sort
		while (!copy.isEmpty()) {
			
			// empty set and search for all non dominated one
			NonDominatedSolutionSet<V> set = new NonDominatedSolutionSet<V>();
			
			for(Solution<V> s : copy) {
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
