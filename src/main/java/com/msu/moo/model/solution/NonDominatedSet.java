package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.msu.moo.interfaces.ISolution;

public class NonDominatedSet<S extends ISolution<?>> implements Iterable<S> {

	// ! list which contains all the solutions
	protected SolutionSet<S> solutions = new SolutionSet<>();

	public NonDominatedSet() {
		super();
	}

	public NonDominatedSet(Collection<? extends S> set) {
		for (S s : set)
			add(s);
	}

	public void addAll(List<S> solutions) {
		for (S entry : solutions)
			add(entry);
	}

	public S get(int index) {
		return solutions.get(index);
	}

	public void addAll(Collection<S> set) {
		for (S s : set)
			add(s);
	}

	/**
	 * CAREFUL: No domination check is made!
	 */
	public boolean addWithoutCheck(S solutionToAdd) {
		return solutions.add(solutionToAdd);
	}

	/**
	 * Add solution by checking if it is dominated. All solutions which are
	 * dominated by the new one are removed!
	 */
	public boolean add(S solutionToAdd) {

		// all the solutions which are dominated by new one
		List<S> areDominated = new ArrayList<>();

		for (S s : solutions) {

			// if the solution objectives are exactly equal
			if (SolutionDominator.isEqual(s, solutionToAdd)) {

				// if variable is null (e.g. after calc median front) dont add
				// it.
				if (s.getVariable() == null || solutionToAdd.getVariable() == null) {
					return false;

					// otherwise test if this solution exists in set
				} else {
					return addEqualSolution(solutionToAdd);
				}
			}

			// if one solution dominates the new one
			else if (SolutionDominator.isDominating(s, solutionToAdd))
				return false;

			// if the new solution dominates one store them in a list
			else if (SolutionDominator.isDominatedBy(s, solutionToAdd))
				areDominated.add(s);

		}

		// remove all dominated solutions and add the new one
		solutions.removeAll(areDominated);
		solutions.add(solutionToAdd);

		return true;
	}

	protected boolean addEqualSolution(S solutionToAdd) {
		for (S s : solutions) {
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
	public SolutionSet<S> getSolutions() {
		return solutions;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (S s : solutions) {
			sb.append(s.toString());
			sb.append("\n");
		}
		if (sb.length() == 0)
			return "";
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * Remove INPLACE all solutions which have violated constraints
	 * 
	 * @return
	 */
	public NonDominatedSet<S> removeSolutionWithConstraintViolations() {
		List<S> l = solutions.stream().filter(s -> s.hasConstrainViolations() == false).collect(Collectors.toList());
		solutions = new SolutionSet<S>(l);
		return this;
	}

	@Override
	public Iterator<S> iterator() {
		return solutions.iterator();
	}

}
