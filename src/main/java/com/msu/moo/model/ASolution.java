package com.msu.moo.model;

import com.msu.moo.interfaces.IVariable;

/**
 * This class combines the variable with the result in the objective space. Each
 * instance is IMMUTABLE which means a solution that is calculate could not be
 * altered anymore.
 * 
 * This concept ensure to forget to reevaluate a solution which means result and
 * variables does not fit anymore.
 */
public abstract class ASolution<T> {

	// ! objectives immutable
	protected T objective;

	// ! variable immutable
	protected IVariable variable;

	// ! returns the number of objectives
	public abstract int countObjectives();

	
	
	public ASolution() {
		super();
	}


	/**
	 * Construct an immutable solution object
	 */
	public ASolution(IVariable variable, T objectives) {
		this.variable = variable;
		this.objective = objectives;
	}

	/**
	 * @return all objectives
	 */
	public T getObjective() {
		return objective;
	}

	/**
	 * @return all variables
	 */
	public IVariable getVariable() {
		return variable;
	}

	/**
	 * Print only the objectives
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(variable);
		sb.append(" -> ");
		sb.append(objective);
		return sb.toString();
	}

}
