package com.msu.moo.model.solution;

import java.util.List;

import com.msu.util.Pair;

/**
 * This class compares different solutions regarding their domination.
 * 
 * Basically, there are the three methods which defines domination,
 * non-domination or equality.
 *
 */
public class SolutionDominator {

	/**
	 * Check if the objective space is the same
	 */
	protected Pair<List<Double>, List<Double>> getObjectives(Solution<?> s1, Solution<?> s2) {
		List<Double> obj1 = s1.getObjectives();
		List<Double> obj2 = s2.getObjectives();
		if (obj1.size() != obj2.size()) {
			throw new RuntimeException("Different objectives space dimensions. Not comparable.");
		}
		return Pair.create(obj1, obj2);
	}

	/**
	 * @return true if s1 dominates s2 (false when equal)
	 */
	public boolean isDominating(Solution<?> s1, Solution<?> s2) {
		Pair<List<Double>, List<Double>> obj = getObjectives(s1, s2);
		int length = obj.first.size();
		for (int i = 0; i < length; i++) {
			// if one objective is one times larger is is not dominating
			if (obj.first.get(i) > obj.second.get(i))
				return false;
		}
		// return only true if they are not equal!
		// because else it would not be a real domination
		return !isEqual(s1, s2);
	}

	
	/**
	 * @return true if s1 is dominated s2 (false when equal)
	 */
	public boolean isDominatedBy(Solution<?> s1, Solution<?> s2) {
		return isDominating(s2, s1);
	}
	

	/**
	 * @return true if both objectives vectors are equal. the variable is not
	 *         checked for equality!
	 */
	public boolean isEqual(Solution<?> s1, Solution<?> s2) {
		Pair<List<Double>, List<Double>> obj = getObjectives(s1, s2);
		final int length = obj.first.size();
		for (int i = 0; i < length; i++) {
			// if one objective is different they are not equal
			if (!obj.first.get(i).equals(obj.second.get(i)))
				return false;
		}
		return true;

	}

	/**
	 * @return true if both solutions are indifferent to each other, which means
	 *         no solution dominates the other one or is dominated by. The
	 *         solution are not allowed to be equal!
	 */
	public boolean isIndifferent(Solution<?> s1, Solution<?> s2) {
		return isDominating(s1, s2) == false && isDominating(s2, s1) == false && !isEqual(s1, s2);
	}

}
