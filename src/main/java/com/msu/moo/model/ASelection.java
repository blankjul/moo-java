package com.msu.moo.model;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.MyRandom;

public abstract class ASelection<V> {


	/**
	 * @return next solution by selection implementation
	 */
	abstract public Solution<V> next();

	
	//! the solution set from which individuals will be selected
	protected SolutionSet<V>  set;
	
	protected MyRandom rand;
	
	public ASelection(SolutionSet<V>  set, MyRandom rand) {
		super();
		this.set = set;
		this.rand = rand;
	}

	/**
	 * @param n number of solutions
	 * @return multiple solutions in base of the given set.
	 */
	public SolutionSet<V>  next(int n) {
		SolutionSet<V>  result = new SolutionSet<V> ();
		for (int i = 0; i < n; i++) {
			result.add(next());
		}
		return result;
	}

}
