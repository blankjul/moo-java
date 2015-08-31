package com.msu.moo.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

/**
 * This analyzer should be used to compare several pareto fronts which belongs
 * to the same problem instance.
 */
public class Analyzer<P extends IProblem> {

	// for each problem map all the fronts
	protected Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> map = new HashMap<>();

	
	public Analyzer() {
		super();
	}

	public Analyzer(Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> map) {
		super();
		this.map = map;
	}
	
	public NonDominatedSolutionSet estimateTrueFront() {
		NonDominatedSolutionSet front = new NonDominatedSolutionSet();
		for (IAlgorithm<P> algorithm : map.keySet()) {
			for (NonDominatedSolutionSet set : map.get(algorithm)) {
				front.addAll(set.getSolutions());
			}
		}
		return front;
	}
	
	

	
}
