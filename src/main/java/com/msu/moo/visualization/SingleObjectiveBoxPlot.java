package com.msu.moo.visualization;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.ProblemFinishedEvent;
import com.msu.moo.util.plots.BoxPlot;

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
				hvs.add(set.get(0).getObjectives(0));
			}
			bp.add(hvs, algorithm.toString());
		}
		showOrPrint(bp, String.format("%s-sbox", problem));
	}

}
