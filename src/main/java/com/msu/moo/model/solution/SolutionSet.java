package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
	
	/**
	 * @return vector of all current solution of given objective
	 */
	public List<Double> getVector(int objective) {
		List<Double> l = new ArrayList<>();
		for (Solution s : this) {
			l.add(s.getObjectives().get(objective));
		}
		return l;
	}
	
	/**
	 * @return normalized front with objectives values between [0,1]
	 */
	public SolutionSet normalize() {
		if (this.isEmpty()) return new SolutionSet();
		int dimensions = this.get(0).getObjectives().size();
		
		double[][] points = new double[dimensions][this.size()];
		
		for (int i = 0; i < dimensions; i++) {
			List<Double> v = getVector(i);
			double min = Collections.min(v);
			double max = Collections.max(v);
			
			for (int k = 0; k < size(); k++) {
				double value = this.get(k).getObjectives().get(i);
				points[i][k] = (value - min) / (max - min);
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
		int dimensions = this.get(0).getObjectives().size();
		
		SolutionSet result = new SolutionSet();
		for (int k = 0; k < size(); k++) {
			List<Double> obj = new ArrayList<>();
			for (int i = 0; i < dimensions; i++) {
				double value = this.get(k).getObjectives().get(i);
				value = max - value;
				if (value > max) value = max;
				obj.add(value);
			}
			result.add(new Solution(this.get(k).getVariable(), obj));
		}
		return result;
	}

	
	
	

}
