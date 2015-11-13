package com.msu.experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
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
		add(problem, algorithm, set, null);
	}
	
	
	public void add(IProblem problem, IAlgorithm algorithm, NonDominatedSolutionSet set, String prefix) {
		String key = String.format("p%s_a%s", problem, algorithm);
		if (prefix != null) key += prefix;
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

	public Collection<NonDominatedSolutionSet> get(IProblem problem, Collection<IAlgorithm> algorithms) {
		Collection<NonDominatedSolutionSet> fronts = new ArrayList<>();
		for( IAlgorithm a : algorithms) {
			fronts.addAll(get(problem, a));
		}
		return fronts;
	}
	
	
	public Collection<NonDominatedSolutionSet> get(IProblem problem, IAlgorithm algorithm) {
		return get(problem, algorithm, null);
	}
	
	public NonDominatedSolutionSet get(IProblem problem, IAlgorithm algorithm, int n) {
		return new ArrayList<>(get(problem, algorithm, null)).get(n);
	}
	
	public Collection<NonDominatedSolutionSet> get(IProblem problem, IAlgorithm algorithm, String prefix) {
		String key = String.format("p%s_a%s", problem, algorithm);
		if (prefix != null) key += prefix;
		return map.get(key);
	}
	
	public NonDominatedSolutionSet getFirst(IProblem problem, IAlgorithm algorithm, String prefix) {
		Collection<NonDominatedSolutionSet> collection = get(problem, algorithm, prefix);
		try {
			return collection.iterator().next();
		} catch (Exception e) {
			throw new RuntimeException(String.format("No Element saved for %s %s.", problem, algorithm));
		}
	}
	

	public void set(IProblem problem, IAlgorithm algorithm, Collection<NonDominatedSolutionSet> sets) {
		String key = String.format("p%s_a%s", problem, algorithm);
		map.removeAll(key);
		for(NonDominatedSolutionSet set : sets) map.put(key, set);
	}


}
