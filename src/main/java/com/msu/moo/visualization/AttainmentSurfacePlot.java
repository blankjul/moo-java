package com.msu.moo.visualization;

import java.util.Collection;

import com.msu.moo.Configuration;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.ScatterPlot;

public class AttainmentSurfacePlot extends ObjectiveSpacePlot {

	//! also plot the true front if it exists
	protected boolean showTrueFront = true;
	
	public AttainmentSurfacePlot() {
		super();
	}

	public AttainmentSurfacePlot(boolean showTrueFront) {
		super();
		this.showTrueFront = showTrueFront;
	}

	@Override
	public void show(AExperiment experiment) {
		for (IProblem problem : experiment.getProblems()) {

			EmpiricalAttainmentFunction eaf = new EmpiricalAttainmentFunction(Configuration.PATH_TO_EAF);
			ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
			
			NonDominatedSolutionSet front = experiment.getOptima().get(problem);
			if (showTrueFront &&  front != null)
				sp.add(front, "TrueFront");
			
			for (IAlgorithm algorithm : experiment.getAlgorithms()) {
				Collection<NonDominatedSolutionSet> set = experiment.getResult().get(problem, algorithm);
				NonDominatedSolutionSet median = eaf.calculate(set);
				sp.add(median, algorithm.toString());
			}
			
			sp.show();
		}
	}
	


}
