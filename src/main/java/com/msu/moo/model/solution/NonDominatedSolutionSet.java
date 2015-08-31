package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.util.Range;

public class NonDominatedSolutionSet {
	
	//! list which contains all the solutions
	protected SolutionSet solutions = new SolutionSet();
	
	//! solution comparator for testing domination
	protected SolutionDominator cmp = new SolutionDominator();
	
	
	public NonDominatedSolutionSet() {
		super();
	}


	public NonDominatedSolutionSet(List<Solution> solutions) {
		for (Solution s : solutions) add(s);
	}
	
	public <T extends List<Solution>> void addAll(T solutions) {
		for (Solution entry : solutions) add(entry);
	}


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
	public SolutionSet getSolutions() {
		return solutions;
	}
	
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Solution s : solutions) {
			sb.append(s.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public Range<Double> getRange() {
		return this.solutions.getRange();
	}
	
	
	

}
