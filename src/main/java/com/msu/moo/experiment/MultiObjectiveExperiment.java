package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.msu.moo.analyzer.HypervolumeAnalyzer;
import com.msu.moo.analyzer.MedianFrontAnalyzer;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Range;
import com.msu.moo.visualization.BoxPlot;
import com.msu.moo.visualization.LinePlot;
import com.msu.moo.visualization.ScatterPlot;

public abstract class MultiObjectiveExperiment<P extends IProblem> extends AbstractExperiment<P> {

	public boolean calcMedianFront = true;
	public boolean calcHypervolume = true;
	public boolean showHypervolumeOverTime = true;

	@Override
	public void visualize() {

		if (fronts == null)
			throw new RuntimeException("No fronts available. Run the experiment before visualizing it!");
		if (calcMedianFront)
			showMedianFronts();
		if (calcHypervolume)
			showHypervolume();
		if (showHypervolumeOverTime)
			showHyperVolumeOverTime();

	}

	protected void showHyperVolumeOverTime() {
		
		
		// estimate the true front
		NonDominatedSolutionSet trueFront = new NonDominatedSolutionSet();
		for (IAlgorithm<P> algorithm : fronts.keySet()) {
			List<NonDominatedSolutionSet> sets = fronts.get(algorithm);
			for (NonDominatedSolutionSet set : sets) {
				trueFront.addAll(set.getSolutions());
			}
		}
		
		// calculate the normalization range
		Range<Double> range = new Range<>();
		for(Solution s : trueFront.getSolutions()) range.add(s.getObjectives());
		
		List<Double> referencePoint = new ArrayList<>();
		for (int i = 0; i < problem.getNumberOfObjectives(); i++) {
			referencePoint.add(1.0001);
		}
		
		LinePlot lp = new LinePlot(problem.toString());
		
		// get median fronts of all times
		for (IAlgorithm<P> algorithm : fronts.keySet()) {
			
			List<List<Double>> series = new ArrayList<>();
			series.add(new ArrayList<>(Arrays.asList(0.0, 0.0)));
			Multimap<Long, NonDominatedSolutionSet> frontCollection = history.get(algorithm);

			for (Long entry : frontCollection.keySet()) {
				
				Double sum = 0d;
				for(NonDominatedSolutionSet set : frontCollection.get(entry)) {
					Hypervolume calcHV = new Hypervolume(pathToHV);
					SolutionSet normalized = set.getSolutions().normalize(range.get());
					double hv = calcHV.calculate(new NonDominatedSolutionSet(normalized), referencePoint);
					sum += hv;
				}
				
				double mean = sum / (double) frontCollection.get(entry).size();
				series.add(new ArrayList<>(Arrays.asList((double) entry, mean)));
			}
			
			lp.add(series, algorithm.toString());
			
		}

		lp.show();

	}

	protected void showMedianFronts() {
		MedianFrontAnalyzer<P> mda = new MedianFrontAnalyzer<>(pathToEAF, fronts);
		Map<IAlgorithm<P>, NonDominatedSolutionSet> median = mda.calcMedianFronts();

		ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
		for (IAlgorithm<P> algorithm : fronts.keySet()) {
			sp.add(median.get(algorithm).getSolutions(), algorithm.toString());
		}
		if (trueFront != null)
			sp.add(trueFront.getSolutions(), "TrueFront");
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
