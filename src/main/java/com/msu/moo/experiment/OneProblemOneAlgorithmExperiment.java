package com.msu.moo.experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.ScatterPlot;



/**
 * This experiment is for plotting the resulting pareto fronts of one algorithm
 * to have a look at different shapes or specific results.
 *
 * @param <P> Defines the kind of problem which should be part of this experiment
 */
public abstract class OneProblemOneAlgorithmExperiment<P extends IProblem> extends AbstractExperiment<P> {

	// ! return the algorithm which should be tested
	protected abstract IAlgorithm<P> getAlgorithm();

	// return the problem instance
	protected abstract P getProblem();

	// return the true front - null if unknown
	protected abstract NonDominatedSolutionSet getTrueFront();

	protected P problem = null;
	protected NonDominatedSolutionSet trueFront = null;
	protected IAlgorithm<P> algorithm = null;

	@Override
	protected List<IAlgorithm<P>> getAlgorithms() {
		algorithm = getAlgorithm();
		return Arrays.asList(algorithm);
	}

	@Override
	protected Map<P, NonDominatedSolutionSet> getProblems() {
		Map<P, NonDominatedSolutionSet> map = new HashMap<>();
		problem = getProblem();
		trueFront = getTrueFront();
		map.put(problem, trueFront);
		return map;
	}

	
	@Override
	protected void visualize() {
		for (Entry<String, NonDominatedSolutionSet> entry : storage.map.entrySet()) {
			ScatterPlot sp = new ScatterPlot();
			if (trueFront != null) sp.add(trueFront, "trueFront");
			sp.add(entry.getValue(), entry.getKey());
			sp.show();
		}
	}
	

}
