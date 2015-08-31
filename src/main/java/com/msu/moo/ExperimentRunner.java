package com.msu.moo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.analyzer.HypervolumeAnalyzer;
import com.msu.moo.analyzer.MedianFrontAnalyzer;
import com.msu.moo.experiment.KursaweExperiment;
import com.msu.moo.model.AbstractExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.BoxPlot;
import com.msu.moo.visualization.ScatterPlot;

public class ExperimentRunner {

	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		execute(new KursaweExperiment(), "Kursawe");

	}

	public static <P extends IProblem> void execute(AbstractExperiment<P> exp, String title) {
		// execute the experiment
		Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> map = exp.run();

		// calculate the hypervolume analyzer
		HypervolumeAnalyzer<P> ha = new HypervolumeAnalyzer<>("vendor/hv-1.3-src/hv", map);
		Map<IAlgorithm<P>, List<Double>> hvs = ha.calcHypervolume();

		// calculate the median front
		MedianFrontAnalyzer<P> mda = new MedianFrontAnalyzer<>("vendor/aft-0.95/eaf", map);
		Map<IAlgorithm<P>, NonDominatedSolutionSet> median = mda.calcMedianFronts();

		ScatterPlot sp = new ScatterPlot(title, "X", "Y");
		for (IAlgorithm<P> algorithm : map.keySet()) {
			sp.add(median.get(algorithm).getSolutions(), algorithm.toString());
		}
		sp.show();

		BoxPlot bp = new BoxPlot(title);
		for (IAlgorithm<P> algorithm : hvs.keySet()) {
			bp.add(hvs.get(algorithm), algorithm.toString());
		}
		bp.show();
	}

}
