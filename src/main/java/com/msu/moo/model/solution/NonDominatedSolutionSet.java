package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.msu.util.Range;

public class NonDominatedSolutionSet implements Iterable<Solution> {

	// ! list which contains all the solutions
	protected SolutionSet solutions = new SolutionSet();

	// ! solution comparator for testing domination
	protected SolutionDominator cmp = new SolutionDominatorWithConstraints();

	public NonDominatedSolutionSet() {
		super();
	}

	public NonDominatedSolutionSet(NonDominatedSolutionSet set) {
		for (Solution s : set.solutions)
			solutions.add(s);
	}

	public NonDominatedSolutionSet(List<Solution> solutions) {
		for (Solution s : solutions)
			add(s);
	}

	public <T extends List<Solution>> void addAll(T solutions) {
		for (Solution entry : solutions)
			add(entry);
	}

	public Solution get(int index) {
		return solutions.get(index);
	}

	public void addAll(Collection<Solution> set) {
		for (Solution s : set) add(s);
	}
	
	public boolean add(Solution solutionToAdd) {

		// all the solutions which are dominated by new one
		List<Solution> areDominated = new ArrayList<>();

		for (Solution s : solutions) {

			// if the solution objectives are exactly equal
			if (cmp.isEqual(s, solutionToAdd)) {
				
				// if variable is null (e.g. after calc median front) dont add it.
				if (s.getVariable() == null || solutionToAdd.getVariable() == null) {
					return false;
					
				// otherwise test if this solution exists in set
				} else {
					return addEqualSolution(solutionToAdd);
				}
			}

			// if one solution dominates the new one
			if (cmp.isDominating(s, solutionToAdd))
				return false;

			// if the new solution dominates one store them in a list
			if (cmp.isDominatedBy(s, solutionToAdd))
				areDominated.add(s);

		}

		// remove all dominated solutions and add the new one
		solutions.removeAll(areDominated);
		solutions.add(solutionToAdd);

		return true;
	}

	protected boolean addEqualSolution(Solution solutionToAdd) {
		for (Solution s : solutions) {
			if (s.getVariable().equals(solutionToAdd.getVariable())) {
				return false;
			}
		}
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
		if (sb.length() == 0)
			return "";
		return sb.substring(0, sb.length() - 1);
	}

	public Range<Double> getRange() {
		return this.solutions.getRange();
	}

	public SolutionDominator getSolutionDominator() {
		return cmp;
	}

	public void setSolutionDominator(SolutionDominator cmp) {
		this.cmp = cmp;
	}

	/**
	 * Remove INPLACE all solutions which have violated constraints
	 * 
	 * @return
	 */
	public NonDominatedSolutionSet removeSolutionWithConstraintViolations() {
		List<Solution> l = solutions.stream().filter(s -> s.hasConstrainViolations() == false).collect(Collectors.toList());
		solutions = new SolutionSet(l);
		return this;
	}

	@Override
	public Iterator<Solution> iterator() {
		return solutions.iterator();
	}

}
