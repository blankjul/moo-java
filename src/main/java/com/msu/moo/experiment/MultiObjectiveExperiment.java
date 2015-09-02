package com.msu.moo.experiment;

import java.util.List;
import java.util.Map;

import com.msu.moo.analyzer.HypervolumeAnalyzer;
import com.msu.moo.analyzer.MedianFrontAnalyzer;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.BoxPlot;
import com.msu.moo.visualization.ScatterPlot;

public abstract class MultiObjectiveExperiment<P extends IProblem> extends AbstractExperiment<P> {

	public boolean calcMedianFront = true;
	
	public boolean calcHypervolume = true;


	@Override
	public void visualize() {

		if (fronts == null)
			throw new RuntimeException("No fronts available. Run the experiment before visualizing it!");
		if (calcMedianFront) showMedianFronts();
		if (calcHypervolume) showHypervolume();
	}
	
	
	protected void showMedianFronts() {
		MedianFrontAnalyzer<P> mda = new MedianFrontAnalyzer<>(pathToEAF, fronts);
		Map<IAlgorithm<P>, NonDominatedSolutionSet> median = mda.calcMedianFronts();

		ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
		for (IAlgorithm<P> algorithm : fronts.keySet()) {
			sp.add(median.get(algorithm).getSolutions(), algorithm.toString());
		}
		if (trueFront != null) sp.add(trueFront.getSolutions(), "TrueFront");
		sp.show();
	}
	
	protected void showHypervolume() {
		HypervolumeAnalyzer<P> ha = new HypervolumeAnalyzer<>(pathToHV, fronts);
		Map<IAlgorithm<P>, List<Double>> hvs = ha.calcHypervolume();
		BoxPlot bp = new BoxPlot(problem.toString());
		for (IAlgorithm<P> algorithm : hvs.keySet()) {
			bp.add(hvs.get(algorithm), algorithm.toString());
			System.out.println(String.format("%s,%s", hvs.get(algorithm), algorithm));
		}
		bp.show();
	}
	

}
