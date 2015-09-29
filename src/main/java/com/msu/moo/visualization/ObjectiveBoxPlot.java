package com.msu.moo.visualization;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.plots.BoxPlot;

public class ObjectiveBoxPlot implements IVisualize {


	@Override
	public void show(AExperiment experiment) {
		
		ExperimentResult result = experiment.getResult();
		
		for (IProblem problem : experiment.getProblems()) {

			BoxPlot bp = new BoxPlot(problem.toString());
			for (IAlgorithm algorithm : experiment.getAlgorithms()) {
				List<Double> hvs = new ArrayList<>();
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					hvs.add(set.get(0).getObjectives(0));
				}
				bp.add(hvs, algorithm.toString());
			}
			if (experiment.hasOutputDirectory()) bp.save(String.format("%s/box_%s.png", experiment.getOutputDir(), problem));
			if (experiment.isVisualize()) bp.show();
		}
		
	}

}
