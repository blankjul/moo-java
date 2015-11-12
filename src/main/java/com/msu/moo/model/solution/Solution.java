package com.msu.moo.model.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.model.ASolution;
import com.msu.moo.util.Pair;

public class Solution extends ASolution<List<Double>> {

	public Solution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}

	public Solution(IVariable variable, List<Double> objectives, List<Double> constraintViolations) {
		super(variable, objectives, constraintViolations);
	}


	/**
	 * @return nth objective
	 */
	public Double getObjectives(int n) {
		return objective.get(n);
	}

	public boolean hasConstrainViolations() {
		for (Double d : constraintViolations) {
			if (!d.equals(0d))
				return true;
		}
		return false;
	}

	public int countObjectives() {
		if (objective == null)
			throw new RuntimeException("objectives are null. no count possible!");
		return objective.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Double d : objective) {
			sb.append(d);
			sb.append(",");
		}
		for (Double d : constraintViolations) {
			sb.append(d);
			sb.append(",");
		}
		sb.append("\"");
		sb.append(variable);
		sb.append("\"");
		return sb.toString();
	}

	
	public Solution normalize(List<Pair<Double, Double>> boundaries) {
		int dimensions = getObjective().size();
		double[] points = new double[dimensions];

		for (int i = 0; i < dimensions; i++) {
			double min = boundaries.get(i).first;
			double max = boundaries.get(i).second;

			// if (min == max) throw new RuntimeException("Error when
			// normalizing: Min is equal to Max!");
			if (min == max)
				min = min - 0.0001;

			double value = getObjective().get(i);
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
		return new Solution(getVariable(), obj, constraintViolations);
		
	}
	
	public Double getMaxConstraintViolation() {
		if (constraintViolations.isEmpty()) return 0d;
		return Collections.max(constraintViolations);
	}
	
	public Double getSumOfConstraintViolation() {
		if (constraintViolations.isEmpty()) return 0d;
		return constraintViolations.stream().mapToDouble(Double::doubleValue).sum();
	}

}
