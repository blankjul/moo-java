package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.msu.moo.util.Pair;
import com.msu.moo.util.Range;

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
		Collections.sort(this, (Solution s1, Solution s2) -> s1.getObjective().get(obj).compareTo(s2.getObjective().get(obj)));
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
	
	/**
	 * @return vector of all current solution of given objective
	 */
	public List<Double> getVector(int objective) {
		List<Double> l = new ArrayList<>();
		for (Solution s : this) {
			l.add(s.getObjective().get(objective));
		}
		return l;
	}
	
	
	/**
	 * Normalize by using the boundaries of this set.
	 */
	public SolutionSet normalize() {
		Range<Double> range = new Range<Double>();
		for (Solution s : this) range.add(s.getObjective());
		return normalize(range.get());
	}
	
	
	/**
	 * The given boundaries are used for maximum and minimum calculation.
	 * @return normalized front with objectives values between [0,1]
	 */
	public SolutionSet normalize(List<Pair<Double,Double>> boundaries) {
		if (this.isEmpty()) return new SolutionSet();
		int dimensions = this.get(0).getObjective().size();
		
		double[][] points = new double[dimensions][this.size()];
		
		for (int i = 0; i < dimensions; i++) {
			double min = boundaries.get(i).first;
			double max = boundaries.get(i).second;
			
			//if (min == max) throw new RuntimeException("Error when normalizing: Min is equal to Max!");
			if (min == max) min = min - 0.0001;
			
			for (int k = 0; k < size(); k++) {
				double value = this.get(k).getObjective().get(i);
				points[i][k] = (value - min) / (max - min);
				
				// even if max or min are not fitting get the values in range
				if (points[i][k] > 1) points[i][k] = 1;
				if (points[i][k] < 0) points[i][k] = 0;
			}
		}
		
		SolutionSet result = new SolutionSet();
		for (int k = 0; k < size(); k++) {
			List<Double> obj = new ArrayList<>();
			for (int i = 0; i < dimensions; i++) {
				obj.add((Double)points[i][k]);
			}
			result.add(new Solution(this.get(k).getVariable(), obj));
		}
		
		return result;
	}
	
	
	/**
	 * This method inverts the front. Which means calculate max - current value.
	 * If the value is larger than max, it is set to max!
	 * @param max value for the inversion
	 * @return Solution that inverted with respect to maximal value
	 */
	public SolutionSet invert(Double max) {
		
		if (this.isEmpty()) return new SolutionSet();
		int dimensions = this.get(0).getObjective().size();
		
		SolutionSet result = new SolutionSet();
		for (int k = 0; k < size(); k++) {
			List<Double> obj = new ArrayList<>();
			for (int i = 0; i < dimensions; i++) {
				double value = this.get(k).getObjective().get(i);
				value = max - value;
				if (value > max) value = max;
				obj.add(value);
			}
			result.add(new Solution(this.get(k).getVariable(), obj));
		}
		return result;
	}
	
	
	public Range<Double> getRange() {
		Range<Double> r = new Range<>();
		for (Solution s : this) {
			r.add(s.getObjective());
		}
		return r;
	}

	
	
	

}
