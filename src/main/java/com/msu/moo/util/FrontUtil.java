package com.msu.moo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.IMultiObjectiveAlgorithm;
import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.plots.BoxPlot;
import com.msu.moo.util.plots.ScatterPlot;

public class FrontUtil {

	public static <P extends IProblem> NonDominatedSolutionSet estimateTrueFront(Map<IMultiObjectiveAlgorithm<P>, List<NonDominatedSolutionSet>> fronts) {
		NonDominatedSolutionSet front = new NonDominatedSolutionSet();
		for (IMultiObjectiveAlgorithm<P> algorithm : fronts.keySet()) {
			for (NonDominatedSolutionSet set : fronts.get(algorithm)) {
				front.addAll(set.getSolutions());
			}
		}
		return front;
	}

	public static <P extends IProblem> ScatterPlot createScatterPlot(String title, Map<IMultiObjectiveAlgorithm<P>, NonDominatedSolutionSet> fronts) {
		ScatterPlot sp = new ScatterPlot(title, "time", "profit");
		for (IMultiObjectiveAlgorithm<P> algorithm : fronts.keySet()) {
			sp.add(fronts.get(algorithm).getSolutions(), algorithm.toString());
		}
		return sp;
	}

	public static <P extends IProblem> BoxPlot createBoxPlot(String title, Map<IMultiObjectiveAlgorithm<P>, List<Double>> hvMap) {
		BoxPlot bp = new BoxPlot(title);
		for(IMultiObjectiveAlgorithm<P> algorithm : hvMap.keySet()) {
			bp.add(hvMap.get(algorithm), algorithm.toString());
		}
		return bp;

	}


	
	public static <P extends IProblem> Map<IMultiObjectiveAlgorithm<P>, NonDominatedSolutionSet> calcMedianFronts(Map<IMultiObjectiveAlgorithm<P>, List<NonDominatedSolutionSet>> fronts,
			String pathToEAF) {
		Map<IMultiObjectiveAlgorithm<P>, NonDominatedSolutionSet> medianFronts = new HashMap<>();
		for (IMultiObjectiveAlgorithm<P> algorithm : fronts.keySet()) {
			NonDominatedSolutionSet medianFront = new EmpiricalAttainmentFunction(pathToEAF).calculate(fronts.get(algorithm));
			medianFronts.put(algorithm, medianFront);
		}
		return medianFronts;
	}

	public static <P extends IProblem> Map<IMultiObjectiveAlgorithm<P>, List<Double>> calcHypervolume(NonDominatedSolutionSet trueFront,
			Map<IMultiObjectiveAlgorithm<P>, List<NonDominatedSolutionSet>> allFronts, String pathToHV) {

		if (trueFront.size() == 0)
			throw new RuntimeException("trueFront is empty!");
		int numOfObjectives = trueFront.getSolutions().get(0).getObjectives().size();

		Map<IMultiObjectiveAlgorithm<P>, List<Double>> hvs = new HashMap<>();
		
		// create reference point for normalized front and get range for
		// normalization
		List<Double> referencePoint = new ArrayList<>();
		for (int i = 0; i < numOfObjectives; i++)
			referencePoint.add(1.0);
		Range<Double> range = trueFront.getRange();

		for (IMultiObjectiveAlgorithm<P> algorithm : allFronts.keySet())
		{
			List<NonDominatedSolutionSet> sets = allFronts.get(algorithm);
			List<Double> l = new ArrayList<>();
			for (NonDominatedSolutionSet set : sets) {
				SolutionSet norm = set.getSolutions().normalize(range.get());
				Double hv = new Hypervolume(pathToHV).calculate(new NonDominatedSolutionSet(norm), referencePoint);
				l.add(hv);
			}
			hvs.put(algorithm, l);
		}
		return hvs;
	}

}
