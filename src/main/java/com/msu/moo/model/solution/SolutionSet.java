package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.msu.util.Pair;
import com.msu.util.Range;

public class SolutionSet<T> extends ArrayList<Solution<T>>{
	

	public SolutionSet() {
		super();
	}

	public SolutionSet(Collection<? extends Solution<T>> c) {
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
	
	
	/**
	 * Normalize by using the boundaries of this set.
	 */
	public SolutionSet<T> normalize() {
		Range<Double> range = new Range<Double>();
		for (Solution<T> s : this) range.add(s.getObjectives());
		return normalize(range.get());
	}
	
	
	/**
	 * The given boundaries are used for maximum and minimum calculation.
	 * @return normalized front with objectives values between [0,1]
	 */
	public SolutionSet<T> normalize(List<Pair<Double,Double>> boundaries) {
		if (this.isEmpty()) return new SolutionSet<T>();
		SolutionSet<T> result = new SolutionSet<T>();
		for (Solution<T> s : this) {
			result.add(s.normalize(boundaries));
		}
		return result;
	}
	
	
	/**
	 * This method inverts the front. Which means calculate max - current value.
	 * If the value is larger than max, it is set to max!
	 * @param max value for the inversion
	 * @return Solution that inverted with respect to maximal value
	 */
	public SolutionSet<T> invert(Double max) {
		
		if (this.isEmpty()) return new SolutionSet<T>();
		int dimensions = this.get(0).getObjectives().size();
		
		SolutionSet<T> result = new SolutionSet<T>();
		for (int k = 0; k < size(); k++) {
			List<Double> obj = new ArrayList<>();
			for (int i = 0; i < dimensions; i++) {
				double value = this.get(k).getObjectives().get(i);
				value = max - value;
				if (value > max) value = max;
				obj.add(value);
			}
			result.add(new Solution<T>(this.get(k).getVariable(), obj));
		}
		return result;
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
