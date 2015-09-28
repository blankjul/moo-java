package com.msu.moo.experiment;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

/**
 * This class is responsible to store all the fronts from the algorithms. Also
 * during the maxEvaluations time.
 *
 */
public class ExperimentResult {

	// ! algorithm mapped to Map with has for each state the evaluation data
	protected Multimap<String, NonDominatedSolutionSet> map = HashMultimap.create();


	public void add(IProblem problem, IAlgorithm algorithm, NonDominatedSolutionSet set) {
		String key = String.format("p%s_a%s", problem, algorithm);
		map.put(key, set);
	}


	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, NonDominatedSolutionSet> entry : map.entries()) {
			sb.append(entry);
		}
		return sb.toString();
	}

	public Multimap<String, NonDominatedSolutionSet> get() {
		return map;
	}

	public Collection<NonDominatedSolutionSet> get(IProblem problem, IAlgorithm algorithm) {
		String key = String.format("p%s_a%s", problem, algorithm);
		return map.get(key);
	}


}
