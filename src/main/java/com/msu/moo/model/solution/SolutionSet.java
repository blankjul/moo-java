package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class SolutionSet extends ArrayList<Solution>{
	

	public SolutionSet() {
		super();
	}

	public SolutionSet(Collection<? extends Solution> c) {
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
		Collections.sort(this, (Solution s1, Solution s2) -> s1.getObjectives().get(obj).compareTo(s2.getObjectives().get(obj)));
	}
	
	/**
	 * Sorts the population by using an indicator.
	 * DEFAULT: Increasing!
	 * @param <T>
	 * @param obj number of objective
	 */
	public <T extends Comparable<T>> void sortByIndicator(Map<Solution, T> m) {
		Collections.sort(this, (Solution s1, Solution s2) -> m.get(s1).compareTo(m.get(s2)));
	}
	
	

}
