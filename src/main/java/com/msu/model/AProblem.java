package com.msu.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public abstract class AProblem<V extends IVariable> implements IProblem {

	/**
	 * Evaluation method that must be implemented by all subclasses.
	 * @param var input value
	 * @return objective results
	 */
	protected abstract void evaluate_(V var, List<Double> objectives, List<Double> constraintViolations);
	
	// ! name of this problem instance
	protected String name = this.getClass().getSimpleName();
	
	//! optimal solution for this problem. null if not set
	protected NonDominatedSolutionSet optimum = null;

	@Override
	public Solution evaluate(IVariable variable) {

		@SuppressWarnings("unchecked")
		V v = (V) variable;

		List<Double> objectives = new ArrayList<>();
		List<Double> constraintViolations = new ArrayList<>();
		
		// use the function to evaluate
		evaluate_(v, objectives, constraintViolations);
		
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
		
		return new Solution(variable, objectives, constraintViolations);
	}
	
	
	
	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public NonDominatedSolutionSet getOptimum() {
		return optimum;
	}


	public void setOptimum(NonDominatedSolutionSet optimum) {
		this.optimum = optimum;
	}
	
	

}
