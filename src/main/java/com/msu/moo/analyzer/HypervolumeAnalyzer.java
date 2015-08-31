package com.msu.moo.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class HypervolumeAnalyzer<P extends IProblem> extends Analyzer<P> {

	
	//! the true front for the problem (null if unknown)
	protected NonDominatedSolutionSet trueFront = null;

	// path to Fonseca's hypervolume implementation
	protected String pathToHV = null;
	
	
	public HypervolumeAnalyzer(String pathToHV, Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> map) {
		super();
		this.pathToHV = pathToHV;
		this.map = map;
	}
	
	public HypervolumeAnalyzer(String pathToHV, Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> map, NonDominatedSolutionSet trueFront) {
		super();
		this.pathToHV = pathToHV;
		this.map = map;
		this.trueFront = trueFront;
	}


	/**
	 * Add a NonDominatedSolution set to the analyzer.
	 * @param a algorithm which was used.
	 * @param set which was calculated as front
	 */
	public void add(IAlgorithm<P> a, NonDominatedSolutionSet set ) {
		if (map.get(a)==null) map.put(a, new ArrayList<NonDominatedSolutionSet>());
	}
	
	/**
	 * @return hypervolume normalized to the estimated front
	 */
	public Map<IAlgorithm<P>, List<Double>> calcHypervolume() {
		return calcHypervolume(estimateTrueFront());
	}
	
	
	public Map<IAlgorithm<P>, List<Double>> calcHypervolume(NonDominatedSolutionSet trueFront) {
		return calcHypervolume(trueFront.getRange());
	}
	
	
	public Map<IAlgorithm<P>, List<Double>> calcHypervolume(Range<Double> range) {

		Map<IAlgorithm<P>, List<Double>> hvs = new HashMap<>();
		
		List<Double> referencePoint = new ArrayList<>();
		for (int i = 0; i < range.size(); i++) referencePoint.add(1.0);

		for (IAlgorithm<P> algorithm : map.keySet())
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
	

	

	
}
