package com.msu.moo.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Range;

/**
 * This analyzer should be used to compare several pareto fronts which belongs
 * to the same problem instance.
 */
public class Analyzer {

	// for each problem map all the fronts
	protected Map<IAlgorithm<IProblem>, List<NonDominatedSolutionSet>> map = new HashMap<>();
	
	//! the true front for the problem (null if unknown)
	protected NonDominatedSolutionSet trueFront = null;

	// path to Fonseca's EAF implementation
	protected String pathToEAF = null;
	
	// path to Fonseca's Hypervolume implementation
	protected String pathToHV = null;

	
	
	/**
	 * Add a NonDominatedSolution set to the analyzer.
	 * @param a algorithm which was used.
	 * @param set which was calculated as front
	 */
	public void add(IAlgorithm<IProblem> a, NonDominatedSolutionSet set ) {
		if (map.get(a)==null) map.put(a, new ArrayList<NonDominatedSolutionSet>());
	}
	
	/**
	 * @return hypervolume normalized to the estimated front
	 */
	public Map<IAlgorithm<IProblem>, List<Double>> calcHypervolume() {
		return calcHypervolume(estimateTrueFront());
	}
	
	
	public Map<IAlgorithm<IProblem>, List<Double>> calcHypervolume(NonDominatedSolutionSet trueFront) {
		return calcHypervolume(trueFront.getRange());
	}
	
	
	public Map<IAlgorithm<IProblem>, List<Double>> calcHypervolume(Range<Double> range) {

		Map<IAlgorithm<IProblem>, List<Double>> hvs = new HashMap<>();
		
		List<Double> referencePoint = new ArrayList<>();
		for (int i = 0; i < range.size(); i++) referencePoint.add(1.0);

		for (IAlgorithm<IProblem> algorithm : map.keySet())
		{
			List<NonDominatedSolutionSet> sets = map.get(algorithm);
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
	
	
	public NonDominatedSolutionSet estimateTrueFront() {
		NonDominatedSolutionSet front = new NonDominatedSolutionSet();
		for (IAlgorithm<IProblem> algorithm : map.keySet()) {
			for (NonDominatedSolutionSet set : map.get(algorithm)) {
				front.addAll(set.getSolutions());
			}
		}
		return front;
	}
	
	
	public Map<IAlgorithm<IProblem>, NonDominatedSolutionSet> calcMedianFronts() {
		Map<IAlgorithm<IProblem>, NonDominatedSolutionSet> medianFronts = new HashMap<>();
		for (IAlgorithm<IProblem> algorithm : map.keySet()) {
			NonDominatedSolutionSet medianFront = new EmpiricalAttainmentFunction(pathToEAF).calculate(map.get(algorithm));
			medianFronts.put(algorithm, medianFront);
		}
		return medianFronts;
	}
	
	
	
}
