package com.msu.moo.model.solution;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.ASolution;


public class Solution extends ASolution<List<Double>>{


	public Solution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}
	
	public Solution(IVariable variable, List<Double> objectives, List<Double> constraintViolations) {
		super(variable, objectives, constraintViolations);
	}



	public Solution(SingleObjectiveSolution solution) {
		this.variable = solution.getVariable();
		this.objective = Arrays.asList(solution.getObjective());
		this.constraintViolations = solution.getConstraintViolations();
	}
	
	
	/**
	 * @return nth objective
	 */
	public Double getObjectives(int n) {
		return objective.get(n);
	}
	
	
	public boolean hasConstrainViolations() {
		for (Double d : constraintViolations) {
			if (!d.equals(0d)) return true;
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

	
}
