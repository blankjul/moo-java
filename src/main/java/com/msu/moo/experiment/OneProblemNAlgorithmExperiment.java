package com.msu.moo.experiment;import java.util.HashMap;
import java.util.Map;

import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public abstract class OneProblemNAlgorithmExperiment<P extends IProblem> extends NProblemNAlgorithmExperiment<P>{


	// return the problem instance
	protected abstract P getProblem();
	
	// return the true front - null if unknown
	protected abstract NonDominatedSolutionSet getTrueFront();
	
	
	protected P problem = null;
	protected NonDominatedSolutionSet trueFront = null;
	

	@Override
	protected Map<P, NonDominatedSolutionSet> getProblems() {
		Map<P, NonDominatedSolutionSet> map = new HashMap<>();
		problem = getProblem();
		trueFront = getTrueFront();
		map.put(problem, trueFront);
		return map;
	}

	
	@Override
	public void report() {
		super.visualize(problem);
	}
	

}
