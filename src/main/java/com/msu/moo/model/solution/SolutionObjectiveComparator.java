package com.msu.moo.model.solution;

import java.util.Comparator;

import com.msu.moo.interfaces.ISolution;

/**
 * Comparator for sorting solutions according the given constraints and
 * objective values.
 *
 * @param <S>
 *            solution type
 */
public class SolutionObjectiveComparator<S extends ISolution<?>> implements Comparator<S> {

	@Override
	public int compare(S o1, S o2) {
		int constraint = Double.compare(o1.getSumOfConstraintViolation(), o2.getSumOfConstraintViolation());
		if (constraint != 0)
			return constraint;
		else {
			for (int i = 0; i < o1.numOfObjectives(); i++) {
				int value = Double.compare(o1.getObjective(i), o2.getObjective(i));
				if (value != 0)
					return value;
			}
			return 0;
		}
	}

}
