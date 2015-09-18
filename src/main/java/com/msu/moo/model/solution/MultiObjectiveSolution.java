package com.msu.moo.model.solution;

import java.util.List;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.ASolution;


public class MultiObjectiveSolution extends ASolution<List<Double>>{

	/**
	 * Construct an immutable solution object
	 */
	public MultiObjectiveSolution(IVariable variable, List<Double> objectives) {
		super(variable, objectives);
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
