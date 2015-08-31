package com.msu.moo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IExperiment;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.FrontUtil;
import com.msu.moo.visualization.ScatterPlot;

public abstract class AbstractExperiment<P extends IProblem> implements IExperiment {

	// ! all algorithms that should be evaluated for this experiment
	protected abstract List<IAlgorithm<P>> getAlgorithms();

	// ! all problem instances that should be solved
	protected abstract List<P> getProblems();

	// ! number of iterations for each algorithm
	abstract public int getIterations();

	// ! number of evaluations as stopping criterion
	abstract public long getMaxEvaluations();

	
	protected Map<IAlgorithm<P>, NonDominatedSolutionSet> medianFronts = new HashMap<>();
	protected Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> allFronts = new HashMap<>();
	protected Map<IAlgorithm<P>, List<Double>> hvs = new HashMap<>();

	public void run() {
		
		List<P> problems = getProblems();
		List<IAlgorithm<P>> algorithms = getAlgorithms();
		
		Map<IProblem, NonDominatedSolutionSet> trueFronts = getTrueFronts(problems);
		allFronts.clear();
		
		for (P problem : problems) {

			// calculate the result for each algorithm
			for (IAlgorithm<P> algorithm : algorithms) {
				algorithm.setMaxEvaluations(getMaxEvaluations());
				List<NonDominatedSolutionSet> sets = new ArrayList<>();
				for (int i = 0; i < getIterations(); i++) {
					sets.add(algorithm.run(problem));
				}
				allFronts.put(algorithm, sets);
			}

			// if the true front is given take this. otherwise estimate it by having a look
			// all calculate fronts from all algorithm and create from that results the best front
			NonDominatedSolutionSet trueFront = null;
			if (trueFronts != null) trueFront = trueFronts.get(problem);
			// if there is not true pareto front
			if (trueFront == null) trueFront =  FrontUtil.estimateTrueFront(allFronts);
			
			// show the scatter plot of median fronts
			medianFronts = FrontUtil.calcMedianFronts(allFronts, getPathToEAF());
			ScatterPlot sp = FrontUtil.createScatterPlot(problem.toString(), medianFronts);
			sp.add(trueFront.getSolutions(), "TrueFront");
			sp.show();
			
			// calculate hypervolume of all the fronts using normalization
			hvs = FrontUtil.calcHypervolume(trueFront, allFronts, getPathToHV());
			FrontUtil.createBoxPlot(problem.toString(), hvs).show();
			
			
			// sp.save(String.format("experiment/%s.png", problem));
			// bp.save(String.format("experiment/%s_hv.png", problem));
		}

	}
	

	public String getPathToEAF() {
		return "vendor/aft-0.95/eaf";
	};

	public String getPathToHV() {
		return "vendor/hv-1.3-src/hv";
	}
	

}
