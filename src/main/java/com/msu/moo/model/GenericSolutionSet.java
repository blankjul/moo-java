package com.msu.moo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Range;

public class GenericSolutionSet<S extends Solution<T>, T> extends ArrayList<S>{
	

	public GenericSolutionSet() {
		super();
	}

	public GenericSolutionSet(Collection<S> c) {
		super(c);
	}

	public GenericSolutionSet(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Sorts the population by using a specific objective.
	 * DEFAULT: Increasing!
	 * @param obj number of objective
	 */
	public void sortByObjective(int obj) {
		Collections.sort(this, (Solution<T> s1, Solution<T> s2) -> s1.getObjective(obj).compareTo(s2.getObjective(obj)));
	}
	
	/**
	 * Sorts the population by using an indicator.
	 * DEFAULT: Increasing!
	 * @param <T>
	 * @param obj number of objective
	 */
	public <C extends Comparable<C>> void sortByIndicator(Map<Solution<T>, C> m) {
		Collections.sort(this, (Solution<T> s1, Solution<T> s2) -> m.get(s1).compareTo(m.get(s2)));
	}
	
	/**
	 * @return vector of all current solution of given objective
	 */
	public List<Double> getVector(int objective) {
		List<Double> l = new ArrayList<>();
		for (Solution<T> s : this) {
			l.add(s.getObjectives().get(objective));
		}
		return l;
	}
	
	
	public Range<Double> getRange() {
		Range<Double> r = new Range<>();
		for (Solution<T> s : this) {
			r.add(s.getObjectives());
		}
		return r;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Solution<T> s : this) {
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
	}

	
	
	
	

}
