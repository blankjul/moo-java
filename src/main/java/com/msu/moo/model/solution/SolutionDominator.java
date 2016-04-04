package com.msu.moo.model.solution;

import java.util.List;

import com.msu.moo.interfaces.ISolution;

/**
 * This class compares different solutions regarding their domination.
 * 
 * Basically, there are the three methods which defines domination,
 * non-domination or equality.
 *
 */
public class SolutionDominator {

	//! only static class
	private SolutionDominator() {}
	

	/**
	 * @return true if s1 dominates s2 (false when equal)
	 */
	public static boolean isDominating(ISolution<?> s1, ISolution<?> s2) {
		
		// constraints are more important than objectives in the beginning.
		if (s1.getSumOfConstraintViolation() < s2.getSumOfConstraintViolation()) return true;
		else if (s1.getSumOfConstraintViolation() > s2.getSumOfConstraintViolation()) return false;
			
		// if constraint violations are equal
		List<Double> obj1 = s1.getObjectives();
		List<Double> obj2 = s2.getObjectives();
		
		int length = obj1.size();
		for (int i = 0; i < length; i++) {
			// if one objective is one times larger is is not dominating
			if (obj1.get(i) > obj2.get(i))
				return false;
		}
		// return only true if they are not equal!
		// because else it would not be a real domination
		return !isEqual(s1, s2);
	}

	
	/**
	 * @return true if s1 is dominated s2 (false when equal)
	 */
	public static boolean isDominatedBy(ISolution<?> s1, ISolution<?> s2) {
		return isDominating(s2, s1);
	}
	

	/**
	 * @return true if both objectives vectors are equal. the variable is not
	 *         checked for equality!
	 */
	public static boolean isEqual(ISolution<?> s1, ISolution<?> s2) {
		
		if (!s1.getConstraintViolations().equals(s2.getConstraintViolations())) 
			return false;
		
		List<Double> obj1 = s1.getObjectives();
		List<Double> obj2 = s2.getObjectives();
		
		final int length = obj1.size();
		for (int i = 0; i < length; i++) {
			// if one objective is different they are not equal
			if (!obj1.get(i).equals(obj2.get(i)))
				return false;
		}
		return true;

	}

	/**
	 * @return true if both solutions are indifferent to each other, which means
	 *         no solution dominates the other one or is dominated by. The
	 *         solution are not allowed to be equal!
	 */
	public static boolean isIndifferent(ISolution<?> s1, ISolution<?> s2) {
		return isDominating(s1, s2) == false && isDominating(s2, s1) == false && !isEqual(s1, s2);
	}

}
