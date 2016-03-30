package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.msu.moo.util.Range;

public class NonDominatedSolutionSet<V> implements Iterable<Solution<V>> {

	// ! list which contains all the solutions
	protected SolutionSet<V> solutions = new SolutionSet<V>();


	public NonDominatedSolutionSet() {
		super();
	}

	public NonDominatedSolutionSet(NonDominatedSolutionSet<V> set) {
		for (Solution<V> s : set.solutions)
			solutions.add(s);
	}

	public <S extends Solution<V>> NonDominatedSolutionSet(List<S> solutions) {
		for (S s : solutions)
			add(s);
	}

	public void addAll(List<Solution<V>> solutions) {
		for (Solution<V> entry : solutions) add(entry);
	}

	public Solution<V> get(int index) {
		return solutions.get(index);
	}

	public void addAll(Collection<Solution<V>> set) {
		for (Solution<V> s : set) add(s);
	}
	
	public boolean add(Solution<V> solutionToAdd) {

		// all the solutions which are dominated by new one
		List<Solution<V>> areDominated = new ArrayList<>();

		for (Solution<V> s : solutions) {

			// if the solution objectives are exactly equal
			if (SolutionDominator.isEqual(s, solutionToAdd)) {
				
				// if variable is null (e.g. after calc median front) dont add it.
				if (s.getVariable() == null || solutionToAdd.getVariable() == null) {
					return false;
					
				// otherwise test if this solution exists in set
				} else {
					return addEqualSolution(solutionToAdd);
				}
			}

			// if one solution dominates the new one
			if (SolutionDominator.isDominating(s, solutionToAdd))
				return false;

			// if the new solution dominates one store them in a list
			if (SolutionDominator.isDominatedBy(s, solutionToAdd))
				areDominated.add(s);

		}

		// remove all dominated solutions and add the new one
		solutions.removeAll(areDominated);
		solutions.add(solutionToAdd);

		return true;
	}

	protected boolean addEqualSolution(Solution<V> solutionToAdd) {
		for (Solution<V> s : solutions) {
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
	public SolutionSet<V> getSolutions() {
		return solutions;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Solution<V> s : solutions) {
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


	/**
	 * Remove INPLACE all solutions which have violated constraints
	 * 
	 * @return
	 */
	public NonDominatedSolutionSet<V> removeSolutionWithConstraintViolations() {
		List<Solution<V>> l = solutions.stream().filter(s -> s.hasConstrainViolations() == false).collect(Collectors.toList());
		solutions = new SolutionSet<V>(l);
		return this;
	}

	@Override
	public Iterator<Solution<V>> iterator() {
		return solutions.iterator();
	}

	

}
