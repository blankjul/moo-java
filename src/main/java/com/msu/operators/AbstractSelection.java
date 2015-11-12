package com.msu.operators;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.Random;

public abstract class AbstractSelection{

	//! the solution set from which individuals will be selected
	protected SolutionSet set;
	
	protected Random rand;
	
	public AbstractSelection(SolutionSet set) {
		super();
		this.set = set;
	}
	

	public AbstractSelection(SolutionSet set, Random rand) {
		super();
		this.set = set;
		this.rand = rand;
	}



	/**
	 * @return next solution by selection implementation
	 */
	abstract public Solution next();

	
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
