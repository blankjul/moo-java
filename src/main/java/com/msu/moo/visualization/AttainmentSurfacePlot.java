package com.msu.moo.visualization;

import java.util.Collection;

import com.msu.Configuration;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.AVisualize;
import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ProblemFinishedEvent;
import com.msu.util.plots.ScatterPlot;

public class AttainmentSurfacePlot extends AVisualize implements IListener<ProblemFinishedEvent>{

	protected boolean showTrueFront = false;
	
	public AttainmentSurfacePlot() {
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, this);
	}
	
	public AttainmentSurfacePlot(boolean showTrueFront) {
		this();
		this.showTrueFront = showTrueFront;
	}


	@Override
	public void handle(ProblemFinishedEvent event) {
		IProblem problem = event.getProblem();
		AExperiment experiment = event.getExperiment();
		
		EmpiricalAttainmentFunction eaf = new EmpiricalAttainmentFunction(Configuration.PATH_TO_EAF);
		ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
		
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			Collection<NonDominatedSolutionSet> set = experiment.getResult().get(problem, algorithm);
			NonDominatedSolutionSet median = eaf.calculate(set);
			experiment.getResult().add(problem, algorithm, median, "median");
			sp.add(median, algorithm.toString());
		}
		if (showTrueFront) sp.add(problem.getOptimum(), "True Front");
		showOrPrint(sp, String.format("%s-eaf", problem));
		
	}




}
