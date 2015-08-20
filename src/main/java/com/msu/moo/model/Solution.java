package com.msu.moo.model;

import java.util.List;

import com.msu.moo.model.interfaces.IVariable;

/**
 * This class combines the variable with the result in the objective space. Each
 * instance is IMMUTABLE which means a solution that is calculate could not be altered anymore.
 * 
 * This concept ensure to forget to reevaluate a solution which means result and variables does
 * not fit anymore.
 */
public class Solution {

	//! objectives immutable
	final private List<Double> objectives;

	//! variable immutable
	final private IVariable<?> variable;

	/**
	 * Construct an immutable solution object
	 */
	public Solution(IVariable<?> variable, List<Double> objectives) {
		this.variable = variable;
		this.objectives = objectives;
	}
	
	/**
	 * @return  all objectives
	 */
	public List<Double> getObjectives() {
		return objectives;
	}

	/**
	 * @return all variables
	 */
	public IVariable<?> getVariable() {
		return variable;
	}

}
