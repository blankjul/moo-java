package com.msu.moo.experiment;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

/**
 * This class is responsible to store all the fronts from the algorithms.
 * Also during the maxEvaluations time.
 *
 */
public class ParetoFrontStorage {
	
	//! algorithm mapped to Map with has for each state the evaluation data
	protected Map<IAlgorithm<?>, Multimap<Long, NonDominatedSolutionSet>> map = new HashMap<>();
	
	
	public void add(IAlgorithm<?> algorithm, long evaluations, NonDominatedSolutionSet set) {
		
		if (map.get(algorithm) == null) {
			Multimap<Long,NonDominatedSolutionSet> res = HashMultimap.create();
			map.put(algorithm, res);
		}
		Multimap<Long,NonDominatedSolutionSet> multiMap = map.get(algorithm);
		multiMap.put(evaluations, set);
	}
	
	public Multimap<Long, NonDominatedSolutionSet> get(IAlgorithm<?> a) {
		return map.get(a);
	}
	
	
	
	

}
