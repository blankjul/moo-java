package com.msu.moo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.analyzer.HypervolumeAnalyzer;
import com.msu.moo.analyzer.MedianFrontAnalyzer;
import com.msu.moo.experiment.AbstractExperiment;
import com.msu.moo.experiment.KursaweExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.BoxPlot;
import com.msu.moo.visualization.ScatterPlot;

public class ExperimentRunner {

	public final static long maxEvaluations = 50000;
	public final static int iterations = 10;
	public final static long seed = 123;

	public String pathToEAF = "vendor/aft-0.95/eaf";
	public String pathToHV = "vendor/hv-1.3-src/hv";

	public boolean calcMedianFront = true;
	public boolean calcHypervolume = true;

	public static void main(String[] args) {
		BasicConfigurator.configure();
		new ExperimentRunner().execute(new KursaweExperiment(), "Kursawe");
	}

	public <P extends IProblem> void execute(AbstractExperiment<P> exp, String title, long maxEvaluations, int iterations, long seed) {
		// execute the experiment
		Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> map = exp.run(maxEvaluations, iterations, seed);


		// calculate the median front
		if (calcMedianFront) {
			MedianFrontAnalyzer<P> mda = new MedianFrontAnalyzer<>(pathToEAF, map);
			Map<IAlgorithm<P>, NonDominatedSolutionSet> median = mda.calcMedianFronts();

			ScatterPlot sp = new ScatterPlot(title, "X", "Y");
			for (IAlgorithm<P> algorithm : map.keySet()) {
				sp.add(median.get(algorithm).getSolutions(), algorithm.toString());
			}
			sp.show();
		}

		// calculate the hypervolume analyzer
		if (calcHypervolume) {
			HypervolumeAnalyzer<P> ha = new HypervolumeAnalyzer<>(pathToHV, map);
			Map<IAlgorithm<P>, List<Double>> hvs = ha.calcHypervolume();
			BoxPlot bp = new BoxPlot(title);
			for (IAlgorithm<P> algorithm : hvs.keySet()) {
				bp.add(hvs.get(algorithm), algorithm.toString());
				System.out.println(String.format("%s,%s", hvs.get(algorithm), algorithm));
			}
			bp.show();
		}
		
	}

	public <P extends IProblem> void execute(AbstractExperiment<P> exp, String title) {
		execute(exp, title, maxEvaluations, iterations, seed);
	}

}
