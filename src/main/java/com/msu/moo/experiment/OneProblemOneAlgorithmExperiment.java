package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.visualization.ObjectiveSpacePlot;

/**
 * This experiment is for plotting the resulting pareto fronts of one algorithm
 * to have a look at different shapes or specific results.
 *
 * @param
 * 			<P>
 *            Defines the kind of problem which should be part of this
 *            experiment
 */
public abstract class OneProblemOneAlgorithmExperiment<P extends IProblem> extends OneProblemNAlgorithmExperiment<P> {

	// ! return the algorithm which should be tested
	protected abstract IAlgorithm<P> getAlgorithm();

	@Override
	protected void setAlgorithms(ExperimetSettings<P> settings) {
		settings.addAlgorithm(getAlgorithm());
	}
	
	@Override
	protected Collection<IVisualize<P>> getVisualizer() {
		List<IVisualize<P>> visualizer =  new ArrayList<IVisualize<P>>();
		visualizer.add(new ObjectiveSpacePlot<P>());
		return visualizer;
	}

}
