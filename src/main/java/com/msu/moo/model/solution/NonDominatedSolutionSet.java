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
	
	public NonDominatedSolutionSet(NonDominatedSolutionSet set) {
		for(MultiObjectiveSolution s : set.solutions) solutions.add(s);
	}

	public NonDominatedSolutionSet(List<MultiObjectiveSolution> solutions) {
		for (MultiObjectiveSolution s : solutions) add(s);
	}
	
	public <T extends List<MultiObjectiveSolution>> void addAll(T solutions) {
		for (MultiObjectiveSolution entry : solutions) add(entry);
	}


	public boolean add(MultiObjectiveSolution solutionToAdd) {
		
		// all the solutions which are dominated by new one
		List<MultiObjectiveSolution> areDominated = new ArrayList<>();
		
		for(MultiObjectiveSolution s : solutions) {
			
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
		for (MultiObjectiveSolution s : solutions) {
			sb.append(s.toString());
			sb.append("\n");
		}
		if (sb.length() == 0) return "";
		return sb.substring(0, sb.length() - 1);
	}
	
	public Range<Double> getRange() {
		return this.solutions.getRange();
	}
	
	
	

}
