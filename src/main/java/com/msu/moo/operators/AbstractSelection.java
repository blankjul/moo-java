package com.msu.moo.operators;

import com.msu.moo.model.solution.MultiObjectiveSolution;
import com.msu.moo.model.solution.SolutionSet;

public abstract class AbstractSelection{

	//! the solution set from which individuals will be selected
	protected SolutionSet set;
	
	
	public AbstractSelection(SolutionSet set) {
		super();
		this.set = set;
	}


	/**
	 * @return next solution by selection implementation
	 */
	abstract public MultiObjectiveSolution next();

	
	/**
	 * @param n number of solutions
	 * @return multiple solutions in base of the given set.
	 */
	public SolutionSet next(int n) {
		SolutionSet result = new SolutionSet();
		for (int i = 0; i < n; i++) {
			result.add(next());
		}
		return result;
	}

}
