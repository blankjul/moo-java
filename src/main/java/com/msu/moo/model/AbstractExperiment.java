package com.msu.moo.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.ScatterPlot;

public abstract class AbstractExperiment<P extends IProblem<?>> {
	
	//! default amount of iterations
	protected int iterations = 10;
	
	//! maximal evaluations of each algorithm
	protected long maxEvaluations = 10000L;
	
	//! maximal evaluations of each algorithm
	protected String pathToEAF = "vendor/aft-0.95/eaf";
	
	
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
				algorithm.setMaxEvaluations(maxEvaluations);
				
				List<NonDominatedSolutionSet> sets = new ArrayList<>();
				for (int i = 0; i < iterations; i++) {
					NonDominatedSolutionSet s = algorithm.run(problem);
					sets.add(s);
				}
				
				NonDominatedSolutionSet set = new EmpiricalAttainmentFunction(pathToEAF).calculate(sets);
				sp.add(set.getSolutions(), algorithm.getName());
				
				
				results.add(set);
			}
			sp.show();
			
		}
		
	}
	
}
