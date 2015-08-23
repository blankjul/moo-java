package com.msu.moo.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.ScatterPlot;

public abstract class AbstractExperiment<P extends IProblem<?, P>> {
	
	//! default amount of iterations
	protected int iterations = 10;
	
	//! maximal evaluations of each algorithm
	protected long maxEvaluations = 10000L;
	
	/**
	 * @return all algorithms that should be evaluated for this experiment
	 */
	protected abstract List<IAlgorithm<P>> getAlgorithms();
	
	/**
	 * @return all problem instances that should be solved
	 */
	protected abstract List<P> getProblems();
	

	
	public void run() {
		
		
		for(P problem : getProblems()) {
			
			List<NonDominatedSolutionSet> results = new ArrayList<>();
			ScatterPlot sp = new ScatterPlot(problem.getClass().getSimpleName(), "X", "Y");
			
			for (IAlgorithm<P> algorithm : getAlgorithms()) {
				NonDominatedSolutionSet set = algorithm.run(problem, iterations);
				sp.add(set.getSolutions(), algorithm.getClass().getSimpleName());
				results.add(set);
			}
			sp.show();
			
		}
		
	}
	
}
