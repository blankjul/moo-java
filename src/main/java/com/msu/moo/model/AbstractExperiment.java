package com.msu.moo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Range;
import com.msu.moo.visualization.BoxPlot;
import com.msu.moo.visualization.ScatterPlot;

public abstract class AbstractExperiment<P extends IProblem> {
	
	
	//! default amount of iterations
	protected int iterations = 100;
	
	//! maximal evaluations of each algorithm
	protected long maxEvaluations = 100000L;
	
	protected String pathToEAF = "vendor/aft-0.95/eaf";
	protected String pathToHV = "vendor/hv-1.3-src/hv";
	
	
	/**
	 * @return all algorithms that should be evaluated for this experiment
	 */
	protected abstract List<IAlgorithm<P>> getAlgorithms();
	
	/**
	 * @return all problem instances that should be solved
	 */
	protected abstract List<P> getProblems();
	
	
	
	public void run() {
		
		List<IAlgorithm<P>> algorithms = getAlgorithms();
		
		
		for(P problem : getProblems()) {
			
			ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
			
			Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> detail = new HashMap<>();	
			Map<IAlgorithm<P>, NonDominatedSolutionSet> median = new HashMap<>();	
			
			Range<Double> range = new Range<>();
			
			for (IAlgorithm<P> algorithm : algorithms) {
				algorithm.setMaxEvaluations(maxEvaluations);
				
				// calculate for each algorithm the sets in n runs
				List<NonDominatedSolutionSet> sets = new ArrayList<>();
				for (int i = 0; i < iterations; i++) {
					NonDominatedSolutionSet set = algorithm.run(problem);
					
					// update the range for the normalization
					for(Solution s : set.getSolutions()) range.add(s.getObjectives());
					
					sets.add(set);
				}
				
				detail.put(algorithm, sets);
				
				NonDominatedSolutionSet medianFront = new EmpiricalAttainmentFunction(pathToEAF).calculate(sets);
				sp.add(medianFront.getSolutions(), algorithm.getName());
				median.put(algorithm, medianFront);
				
			}
			
			//sp.save(String.format("experiment/%s.png", problem));
			sp.show();
			
			
			BoxPlot bp = new BoxPlot(problem.toString());
			List<Double> referencePoint = new ArrayList<>();
			for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
				referencePoint.add(1.0);
			}
			System.out.println("problem,algorithm,hv");
			
			// normalize all the fronts according to the maximal range
			for (IAlgorithm<P> algorithm : algorithms)
			{
				List<NonDominatedSolutionSet> sets = detail.get(algorithm);
				List<Double> hvs = new ArrayList<>();
				for (NonDominatedSolutionSet set : sets) {
					SolutionSet norm = set.getSolutions().normalize(range.get());
					Double hv = new Hypervolume(pathToHV).calculate(new NonDominatedSolutionSet(norm), referencePoint);

					
					hvs.add(hv);
				}
				bp.add(hvs, algorithm.getName());
			}
			bp.show();
			//bp.save(String.format("experiment/%s_hv.png", problem));
		}
		
	}
	
}
