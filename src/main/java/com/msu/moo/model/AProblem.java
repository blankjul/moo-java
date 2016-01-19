package com.msu.moo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

public abstract class AProblem<V extends IVariable> implements IProblem<V> {

	/**
	 * Evaluation method that must be implemented by all subclasses.
	 * @param var input value
	 * @return objective results
	 */
	protected abstract void evaluate_(V var, List<Double> objectives, List<Double> constraintViolations);
	
	//! name of the problem
	protected String name;
	
	
	
	public AProblem() {
		super();
		this.name = this.getClass().getSimpleName();
	}

	@Override
	public Solution<V> evaluate(V variable) {

		List<Double> objectives = new ArrayList<>();
		List<Double> constraintViolations = new ArrayList<>();
		
		// use the function to evaluate
		evaluate_(variable, objectives, constraintViolations);
		
		if (objectives.size() != this.getNumberOfObjectives()) {
			throw new RuntimeException(String.format("Problem %s should have %s objectives but evaluation function returned %s.", this, getNumberOfObjectives(), objectives.size()));
		}
		
		if (constraintViolations.size() != this.getNumberOfConstraints()) {
			throw new RuntimeException(String.format("Problem %s should have %s constraints but evaluation function returned %s.", this, getNumberOfConstraints(), constraintViolations.size()));
		}
		
		if (!constraintViolations.isEmpty()) {
			double min = Collections.min(constraintViolations);
			if (min < 0) {
				throw new RuntimeException(String.format("Contraints are not allowed to be lower than 0, but minimal values is %s.", min));
			}
		}
		
		return Solution.create(variable, objectives, constraintViolations);
		
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	

	

}
