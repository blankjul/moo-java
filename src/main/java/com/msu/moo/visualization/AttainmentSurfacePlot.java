package com.msu.moo.visualization;

import java.util.Collection;

import com.msu.moo.Configuration;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.ScatterPlot;

public class AttainmentSurfacePlot<P extends IProblem> extends ObjectiveSpacePlot<P> {

	//! also plot the true front if it exists
	protected boolean showTrueFront = true;
	
	public AttainmentSurfacePlot() {
		super();
	}

	public AttainmentSurfacePlot(boolean showTrueFront) {
		super();
		this.showTrueFront = showTrueFront;
	}

	public void show(ExperimetSettings<P, NonDominatedSolutionSet> settings, ExperimentResult<NonDominatedSolutionSet> result) {
		for (IProblem problem : settings.getProblems()) {
			EmpiricalAttainmentFunction eaf = new EmpiricalAttainmentFunction(Configuration.PATH_TO_EAF);
			ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
			for (IAlgorithm<NonDominatedSolutionSet, ?> algorithm : settings.getAlgorithms()) {
				Collection<NonDominatedSolutionSet> set = result.get(problem, algorithm);
				NonDominatedSolutionSet median = eaf.calculate(set);
				sp.add(median, algorithm.toString());
			}
			
			if (showTrueFront && result.getTrueFront(problem) != null) 
				sp.add(result.getTrueFront(problem), "TrueFront");
			
			sp.show();
		}
	}
}
