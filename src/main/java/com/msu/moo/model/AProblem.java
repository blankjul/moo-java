package com.msu.moo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

/**
 * This class is an abstract problem. The evaluation method is wrapped and has
 * some useful checks which should be done. E.g. always the number of the
 * returned objectives values should fit with the objective values of the
 * problem and the same holds for the constraints.
 *
 * @param <V>
 *            variable type
 */
public abstract class AProblem<V extends IVariable> implements IProblem<V> {

	/**
	 * Evaluation method that must be implemented by all subclasses.
	 * @param var variable to be evaluated
	 * @param objectives objective list that is filled with the values
	 * @param constraintViolations list filled with the constraints
	 */
	protected abstract void evaluate_(V var, List<Double> objectives, List<Double> constraintViolations);

	// ! name of the problem
	protected String name;

	/**
	 * Empty constructor. Name is set to the name of the class.
	 */
	public AProblem() {
		super();
		this.name = this.getClass().getSimpleName();
	}

	/**
	 * Evaluate the variable according the problem instance. 
	 * Note, the constraints are
	 * 	- constraints < 0: is not allowed 
	 *  - constraints == 0: not violated
	 *  - constraints > 0: violated - strength expressed by value
	 */
	@Override
	public Solution<V> evaluate(V variable) {

		List<Double> objectives = new ArrayList<>();
		List<Double> constraintViolations = new ArrayList<>();

		// use the function to evaluate
		evaluate_(variable, objectives, constraintViolations);

		if (objectives.size() != this.getNumberOfObjectives()) {
			throw new RuntimeException(String.format("Problem %s should have %s objectives but evaluation function returned %s.", this, getNumberOfObjectives(),
					objectives.size()));
		}

		if (constraintViolations.size() != this.getNumberOfConstraints()) {
			throw new RuntimeException(String.format("Problem %s should have %s constraints but evaluation function returned %s.", this,
					getNumberOfConstraints(), constraintViolations.size()));
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

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
