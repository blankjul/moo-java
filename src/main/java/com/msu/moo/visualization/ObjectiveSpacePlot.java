package com.msu.moo.visualization;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.ScatterPlot;

public class ObjectiveSpacePlot<P extends IProblem> implements IVisualize<P, NonDominatedSolutionSet> {

	// ! also plot the true front if it exists
	protected boolean showTrueFront = true;

	public ObjectiveSpacePlot() {
		super();
	}

	public ObjectiveSpacePlot(boolean showTrueFront) {
		super();
		this.showTrueFront = showTrueFront;
	}

	@Override
	public void show(ExperimetSettings<P, NonDominatedSolutionSet> settings, ExperimentResult<NonDominatedSolutionSet> result) {
		for (IProblem problem : settings.getProblems()) {
			for (IAlgorithm<?, NonDominatedSolutionSet> algorithm : settings.getAlgorithms()) {
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
					sp.add(set, algorithm.toString());
					if (showTrueFront && result.getTrueFront(problem) != null)
						sp.add(result.getTrueFront(problem), "TrueFront");
					sp.show();
				}
			}
		}
	}
}
