package com.msu.moo.visualization;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.Configuration;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Range;
import com.msu.moo.util.plots.BoxPlot;

public class HypervolumeBoxPlot<P extends IProblem> implements IVisualize<P, NonDominatedSolutionSet> {

	@Override
	public void show(ExperimetSettings<P, NonDominatedSolutionSet> settings, ExperimentResult<NonDominatedSolutionSet> result) {
		for (IProblem problem : settings.getProblems()) {

			NonDominatedSolutionSet trueFront = settings.getOptima().get(problem);

			// plot the hypervolume
			Hypervolume calcHV = new Hypervolume(Configuration.PATH_TO_HYPERVOLUME);

			// create reference point for normalized values
			List<Double> referencePoint = new ArrayList<>();
			for (int i = 0; i < problem.getNumberOfObjectives(); i++)
				referencePoint.add(1.0001);

			// estimate true front if not given and calculate the range for
			// normalization
			if (trueFront == null)
				trueFront = AExperiment.estimateTrueFront(result.get().values());
			Range<Double> range = trueFront.getRange();

			BoxPlot bp = new BoxPlot(problem.toString());
			for (IAlgorithm<?, NonDominatedSolutionSet> algorithm : settings.getAlgorithms()) {
				List<Double> hvs = new ArrayList<>();
				for (NonDominatedSolutionSet set : result.get(problem, algorithm)) {
					SolutionSet norm = set.getSolutions().normalize(range.get());
					Double hv = calcHV.calculate(new NonDominatedSolutionSet(norm), referencePoint);
					hvs.add(hv);
				}
				bp.add(hvs, algorithm.toString());
			}
			bp.show();
		}

	}

}