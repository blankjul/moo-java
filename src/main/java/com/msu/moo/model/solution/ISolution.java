package com.msu.moo.model.solution;

import java.util.List;

public interface ISolution {
	
	
	/**
	 * @return number of objectives of solution
	 */
	public int countObjectives();
	
	
	/**
	 * @return all objectives as list
	 */
	public List<Double> getObjectives();
	
	/**
	 * @return all constraint violations of this solution.
	 */
	public List<Double> getConstraintViolations();

	
}
