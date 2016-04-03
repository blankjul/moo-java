package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.util.Range;

public class SolutionSet<S extends ISolution<?>> extends ArrayList<S>{
	
	
	public SolutionSet() {
		super();
	}	

	public SolutionSet(Collection<S> c) {
		super(c);
	}

	public SolutionSet(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Sorts the population by using a specific objective.
	 * DEFAULT: Increasing!
	 * @param obj number of objective
	 */
	public void sortByObjective(int obj) {
		Collections.sort(this, (ISolution<?> s1, ISolution<?> s2) -> s1.getObjective(obj).compareTo(s2.getObjective(obj)));
	}
	
	
	/**
	 * @return vector of all current solution of given objective
	 */
	public List<Double> getVector(int objective) {
		List<Double> l = new ArrayList<>();
		for (ISolution<?> s : this) {
			l.add(s.getObjectives().get(objective));
		}
		return l;
	}
	
	
	public Range<Double> getRange() {
		Range<Double> r = new Range<>();
		for (ISolution<?> s : this) {
			r.add(s.getObjectives());
		}
		return r;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (ISolution<?> s : this) {
			sb.append(s);
			sb.append("\n");
		}
		return sb.toString();
	}

	
	
	

}
