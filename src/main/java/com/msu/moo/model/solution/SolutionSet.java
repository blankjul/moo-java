package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.msu.moo.model.GenericSolutionSet;
import com.msu.moo.util.Pair;
import com.msu.moo.util.Range;

public class SolutionSet<V> extends GenericSolutionSet<Solution<V>,V>{
	

	public SolutionSet() {
		super();
	}

	public <S extends Solution<V>> SolutionSet(Collection<S> c) {
		for(S entry : c) {
			Solution<V> toAdd = (Solution<V>) entry;
			this.add(toAdd);
		}
	}

	public SolutionSet(int initialCapacity) {
		super(initialCapacity);
	}

	
	/**
	 * Normalize by using the boundaries of this set.
	 */
	public SolutionSet<V> normalize() {
		Range<Double> range = new Range<Double>();
		for (Solution<V> s : this) range.add(s.getObjectives());
		return normalize(range.get());
	}
	
	
	/**
	 * The given boundaries are used for maximum and minimum calculation.
	 * @return normalized front with objectives values between [0,1]
	 */
	public SolutionSet<V> normalize(List<Pair<Double,Double>> boundaries) {
		if (this.isEmpty()) return new SolutionSet<V>();
		SolutionSet<V> result = new SolutionSet<V>();
		for (Solution<V> s : this) {
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
	public SolutionSet<V> invert(Double max) {
		
		if (this.isEmpty()) return new SolutionSet<V>();
		int dimensions = this.get(0).getObjectives().size();
		
		SolutionSet<V> result = new SolutionSet<V>();
		for (int k = 0; k < size(); k++) {
			List<Double> obj = new ArrayList<>();
			for (int i = 0; i < dimensions; i++) {
				double value = this.get(k).getObjectives().get(i);
				value = max - value;
				if (value > max) value = max;
				obj.add(value);
			}
			result.add(new Solution<V>(this.get(k).getVariable(), obj));
		}
		return result;
	}
	
	

	
	
	

}
