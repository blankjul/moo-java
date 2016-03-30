package com.msu.moo.interfaces;

import java.util.List;

public interface ISolution<V> {

	/**
	 * @return objectives values
	 */
	public List<Double> getObjectives();

	/**
	 * @return return nth objective value
	 */
	public Double getObjective(int n);

	/**
	 * @return number of objectives
	 */
	public int numOfObjectives();

	/**
	 * @return variable
	 */
	public V getVariable();

	/**
	 * @return constraint violations of the solution
	 */
	public List<Double> getConstraintViolations();
	
	
	/**
	 * @return sum of all constraint violations.
	 */
	public double getSumOfConstraintViolation();
	
	
	/**
	 * @return whether a solution has constraint violations or not. basically:
	 *         feasible or not.
	 */
	public boolean hasConstrainViolations();
	
	

}
