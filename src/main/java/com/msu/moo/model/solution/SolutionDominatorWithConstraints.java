package com.msu.moo.model.solution;

import java.util.Collections;
import java.util.List;

import com.msu.moo.util.Pair;

public class SolutionDominatorWithConstraints extends SolutionDominator {

	/**
	 * Check if the objective space is the same
	 */
	protected Pair<Double, Double> getConstraintViolations(Solution s1, Solution s2) {
		List<Double> obj1 = s1.getConstraintViolations();
		List<Double> obj2 = s2.getConstraintViolations();
		if (obj1.size() != obj2.size()) {
			throw new RuntimeException("Different constraint violations space dimensions. Not comparable.");
		}
		if (obj1.size() == 0) return  Pair.create(0d,0d);
		else return Pair.create(Collections.max(obj1), Collections.max(obj2));
	}

	@Override
	public boolean isDominating(Solution s1, Solution s2) {
		Pair<Double, Double> p = getConstraintViolations(s1, s2);
		if (!p.first.equals(p.second))
			return p.first < p.second;
		else
			return super.isDominating(s1, s2);
	}

	@Override
	public boolean isDominatedBy(Solution s1, Solution s2) {
		Pair<Double, Double> p = getConstraintViolations(s1, s2);
		if (!p.first.equals(p.second))
			return p.first > p.second;
		else
			return super.isDominating(s1, s2);
	}

	@Override
	public boolean isEqual(Solution s1, Solution s2) {
		Pair<Double, Double> p = getConstraintViolations(s1, s2);
		if (!p.first.equals(p.second))
			return false;
		else
			return super.isEqual(s1, s2);
	}

}
