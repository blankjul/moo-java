package com.msu.moo.visualization;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.ScatterPlot;

public class ObjectiveSpacePlot implements IVisualize {

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
	public void show(AExperiment experiment) {
		for (IProblem problem : experiment.getProblems()) {
			for (IAlgorithm algorithm : experiment.getAlgorithms()) {
				for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
					ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
					sp.add(set, algorithm.toString());
					NonDominatedSolutionSet front = experiment.getOptima().get(problem);
					if (showTrueFront &&  front != null)
						sp.add(front, "TrueFront");
					sp.show();
				}
			}
		}
	}
}
