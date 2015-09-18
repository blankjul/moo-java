package com.msu.moo.model.solution;

import java.util.List;

import com.msu.moo.util.Pair;

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
	protected Pair<List<Double>, List<Double>> getObjectives(MultiObjectiveSolution s1, MultiObjectiveSolution s2) {
		List<Double> obj1 = s1.getObjective();
		List<Double> obj2 = s2.getObjective();
		if (obj1.size() != obj2.size()) {
			throw new RuntimeException("Different objectives space dimensions. Not comparable.");
		}
		return Pair.create(obj1, obj2);
	}

	/**
	 * @return true if s1 dominates s2 (false when equal)
	 */
	public boolean isDominating(MultiObjectiveSolution s1, MultiObjectiveSolution s2) {
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
	 * @return true if s2 dominates s1 (false when equal)
	 */
	public boolean isDominatedBy(MultiObjectiveSolution s1, MultiObjectiveSolution s2) {
		Pair<List<Double>, List<Double>> obj = getObjectives(s1, s2);
		int length = obj.first.size();
		for (int i = 0; i < length; i++) {
			if (obj.second.get(i) > obj.first.get(i))
				return false;
		}
		// return only true if they are not equal!
		// because else it would not be a real domination
		return !isEqual(s1, s2);
	}

	/**
	 * @return true if both objectives vectors are equal. the variable is not
	 *         checked for equality!
	 */
	public boolean isEqual(MultiObjectiveSolution s1, MultiObjectiveSolution s2) {
		Pair<List<Double>, List<Double>> obj = getObjectives(s1, s2);
		int length = obj.first.size();
		for (int i = 0; i < length; i++) {
			// if one objective is different they are not equal
			if (obj.first.get(i) != obj.second.get(i))
				return false;
		}
		return true;

	}

	/**
	 * @return true if both solutions are indifferent to each other, which means
	 *         no solution dominates the other one or is dominated by. The
	 *         solution are not allowed to be equal!
	 */
	public boolean isIndifferent(MultiObjectiveSolution s1, MultiObjectiveSolution s2) {
		return isDominating(s1, s2) == false && isDominating(s2, s1) == false && !isEqual(s1, s2);
	}

}
