package com.msu.moo.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.comparator.SolutionComparator;

public class NonDominatedSet {
	
	//! list which contains all the solutions
	protected List<Solution> solutions = new ArrayList<>();
	
	//! solution comparator for testing domination
	protected SolutionComparator cmp = new SolutionComparator();
	
	public boolean add(Solution solutionToAdd) {
		
		// all the solutions which are dominated by new one
		List<Solution> areDominated = new ArrayList<>();
		
		for(Solution s : solutions) {
			
			// if one solution dominates the new one 
			if (cmp.isDominating(s, solutionToAdd) || cmp.isEqual(s, solutionToAdd)) return false;
			
			// if the new solution dominates one store them in a list
			if (cmp.isDominatedBy(s, solutionToAdd)) areDominated.add(s);
			
		}
		
		// remove all dominated solutions and add the new one
		solutions.removeAll(areDominated);
		solutions.add(solutionToAdd);
		
		return true;
	}
	
	
	/**
	 * @return the current size of the set.
	 */
	public int size() {
		return solutions.size();
	}

	/**
	 * @return all the solutions in a list!
	 */
	public List<Solution> getSolutions() {
		return solutions;
	}
	
	
	

}
