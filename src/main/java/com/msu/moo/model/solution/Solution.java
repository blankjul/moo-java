package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.ASolution;
import com.msu.moo.util.Pair;

public class Solution<V> extends ASolution<V> {

	
	public Solution(V variable, List<Double> objectives) {
		super(variable, objectives);
	}

	public Solution(V variable, List<Double> objectives, List<Double> constraintViolations) {
		super(variable, objectives, constraintViolations);
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Double d : getObjectives()) {
			sb.append(d);
			sb.append(",");
		}
		for (Double d : getConstraintViolations()) {
			sb.append(d);
			sb.append(",");
		}
		sb.append("\"");
		sb.append(variable);
		sb.append("\"");
		return sb.toString();
	}

	
	public Solution<V> normalize(List<Pair<Double, Double>> boundaries) {
		int dimensions = getObjectives().size();
		double[] points = new double[dimensions];

		for (int i = 0; i < dimensions; i++) {
			double min = boundaries.get(i).first;
			double max = boundaries.get(i).second;

			// if (min == max) throw new RuntimeException("Error when
			// normalizing: Min is equal to Max!");
			if (min == max)
				min = min - 0.0001;

			double value = getObjectives().get(i);
			points[i] = (value - min) / (max - min);

			// even if max or min are not fitting get the values in range
			if (points[i] > 1)
				points[i] = 1;
			if (points[i] < 0)
				points[i] = 0;
		}

		List<Double> obj = new ArrayList<>();
		for (int i = 0; i < dimensions; i++) {
			obj.add((Double) points[i]);
		}
		return new Solution<V>(getVariable(), obj, constraintViolations);
		
	}

	
	
	public static <V> Solution<V> create(V variable, List<Double> objectives, List<Double> constraintViolations) {
		return new Solution<>(variable, objectives, constraintViolations);
				
	}
	
	
	
	
	
	
}
