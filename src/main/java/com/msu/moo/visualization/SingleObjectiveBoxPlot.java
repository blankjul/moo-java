package com.msu.moo.visualization;

import java.util.ArrayList;
import java.util.List;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.AVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ProblemFinishedEvent;
import com.msu.util.plots.BoxPlot;

public class SingleObjectiveBoxPlot extends AVisualize implements IListener<ProblemFinishedEvent>{

	
	public SingleObjectiveBoxPlot() {
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, this);
	}


	@Override
	public void handle(ProblemFinishedEvent event) {
		AExperiment experiment = event.getExperiment();
		IProblem problem = event.getProblem();
		
		BoxPlot bp = new BoxPlot(problem.toString());
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			List<Double> hvs = new ArrayList<>();
			for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
				hvs.add(set.get(0).getObjective(0));
			}
			bp.add(hvs, algorithm.toString());
		}
		showOrPrint(bp, String.format("%s-sbox", problem));
	}

}
