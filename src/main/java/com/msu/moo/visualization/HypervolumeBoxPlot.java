package com.msu.moo.visualization;

import java.util.ArrayList;
import java.util.List;

import com.msu.Configuration;
import com.msu.experiment.AExperiment;
import com.msu.experiment.ExperimentResult;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.AVisualize;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.Range;
import com.msu.util.events.IListener;
import com.msu.util.events.impl.EventDispatcher;
import com.msu.util.events.impl.ProblemFinishedEvent;
import com.msu.util.plots.BoxPlot;

public class HypervolumeBoxPlot extends AVisualize implements IListener<ProblemFinishedEvent> {

	
	public HypervolumeBoxPlot() {
		EventDispatcher.getInstance().register(ProblemFinishedEvent.class, this);
	}

	
	public static List<Double> calc(List<NonDominatedSolutionSet> sets, NonDominatedSolutionSet trueFront) {

		// if null estimate it
		if (trueFront == null)
			trueFront = AExperiment.estimateTrueFront(sets);

		// plot the hypervolume
		Hypervolume calcHV = new Hypervolume(Configuration.PATH_TO_HYPERVOLUME);

		// TODO: Bad implemented
		final int numOfObjectives = sets.get(0).get(0).countObjectives();

		// create reference point for normalized values
		List<Double> referencePoint = new ArrayList<>();
		for (int i = 0; i < numOfObjectives; i++)
			referencePoint.add(1.0001);

		Range<Double> range = trueFront.getRange();

		// calculate the hypervolumes from normalized fronts
		List<Double> hvs = new ArrayList<>();
		for (NonDominatedSolutionSet set : sets) {
			SolutionSet norm = set.getSolutions().normalize(range.get());
			Double hv = calcHV.calculate(new NonDominatedSolutionSet(norm), referencePoint);
			hvs.add(hv);
		}
		
		return hvs;
		
	}
	
	
	@Override
	public void handle(ProblemFinishedEvent event) {
		
		IProblem problem = event.getProblem();
		AExperiment experiment = event.getExperiment();
		ExperimentResult result = experiment.getResult();
		
		// plot the hypervolume
		Hypervolume calcHV = new Hypervolume(Configuration.PATH_TO_HYPERVOLUME);

		// create reference point for normalized values
		List<Double> referencePoint = new ArrayList<>();
		for (int i = 0; i < problem.getNumberOfObjectives(); i++)
			referencePoint.add(1.0001);

		Range<Double> range = problem.getOptimum().getRange();

		BoxPlot bp = new BoxPlot(problem.toString());
		for (IAlgorithm algorithm : experiment.getAlgorithms()) {
			List<Double> hvs = new ArrayList<>();
			for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
				SolutionSet norm = set.getSolutions().normalize(range.get());
				Double hv = calcHV.calculate(new NonDominatedSolutionSet(norm), referencePoint);
				hvs.add(hv);
			}
			bp.add(hvs, algorithm.toString());
		}
		showOrPrint(bp, String.format("%s-box", problem));
	}

}
