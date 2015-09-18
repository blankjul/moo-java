package com.msu.moo.experiment;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.interfaces.IProblem;

/**
 * This class is responsible to store all the fronts from the algorithms. Also
 * during the maxEvaluations time.
 *
 */
public class ExperimentResult<S> {
	
	protected ExperimetSettings<?, S> settings;

	// ! algorithm mapped to Map with has for each state the evaluation data
	protected Multimap<String, S> map = HashMultimap.create();
	
	
	public ExperimentResult(ExperimetSettings<?, S> settings) {
		this.settings = settings;
	}

	public void add(IProblem problem, IAlgorithm<?, S> algorithm, S set) {
		String key = String.format("p%s_a%s", problem, algorithm);
		map.put(key, set);
	}


	public S getTrueFront(IProblem problem) {
		return settings.mOptima.get(problem);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, S> entry : map.entries()) {
			sb.append(entry);
		}
		return sb.toString();
	}

	public Multimap<String, S> get() {
		return map;
	}
	
	public Collection<S> get(IProblem problem, IAlgorithm<?, S> algorithm) {
		String key = String.format("p%s_a%s", problem, algorithm);
		return map.get(key);
	}

	public ExperimetSettings<?, S> getSettings() {
		return settings;
	}



}
