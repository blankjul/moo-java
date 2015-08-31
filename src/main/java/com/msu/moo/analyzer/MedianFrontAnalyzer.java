package com.msu.moo.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class MedianFrontAnalyzer<P extends IProblem> extends Analyzer<P>{
	
	
	// path to Fonseca's EAF implementation
	protected String pathToEAF = null;
	
	
	public MedianFrontAnalyzer() {
		super();
	}


	public MedianFrontAnalyzer(String pathToEAF, Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> map) {
		super(map);
		this.pathToEAF = pathToEAF;
	}


	public Map<IAlgorithm<P>, NonDominatedSolutionSet> calcMedianFronts() {
		Map<IAlgorithm<P>, NonDominatedSolutionSet> medianFronts = new HashMap<>();
		for (IAlgorithm<P> algorithm : map.keySet()) {
			NonDominatedSolutionSet medianFront = new EmpiricalAttainmentFunction(pathToEAF).calculate(map.get(algorithm));
			medianFronts.put(algorithm, medianFront);
		}
		return medianFronts;
	}
	
	

}
