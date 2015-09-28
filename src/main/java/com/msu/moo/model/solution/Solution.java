package com.msu.moo.model.solution;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.ASolution;


public class Solution extends ASolution<List<Double>>{

	
	/**
	 * Construct an immutable solution object
	 */
	public Solution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}
	
	
	public Solution(SingleObjectiveSolution solution) {
		this.variable = solution.getVariable();
		this.objective = Arrays.asList(solution.getObjective());
	}
	
	
	/**
	 * @return nth objective
	 */
	public Double getObjectives(int n) {
		return objective.get(n);
	}
	

	public int countObjectives() {
		if (objective == null)
			throw new RuntimeException("objectives are null. no count possible!");
		return objective.size();
	}

	
}
