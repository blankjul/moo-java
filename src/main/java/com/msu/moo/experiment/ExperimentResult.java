package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

/**
 * This class is responsible to store all the fronts from the algorithms. Also
 * during the maxEvaluations time.
 *
 */
public class ExperimentResult {

	// ! algorithm mapped to Map with has for each state the evaluation data
	protected Map<String, NonDominatedSolutionSet> map = new HashMap<>();

	public void add(IProblem problem, IAlgorithm<?> algorithm, int iteration, NonDominatedSolutionSet set) {
		String key = String.format("p%s_a%s_i%s", problem, algorithm, iteration);
		map.put(key, set);
	}

	public void addTrueFront(IProblem problem, NonDominatedSolutionSet set) {
		String key = String.format("p%s", problem);
		map.put(key, set);
	}

	public NonDominatedSolutionSet get(IProblem problem, IAlgorithm<?> algorithm, int iteration) {
		String key = String.format("p%s_a%s_i%s", problem, algorithm, iteration);
		return map.get(key);
	}

	public NonDominatedSolutionSet getTrueFront(IProblem problem) {
		String key = String.format("p%s", problem);
		return map.get(key);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, NonDominatedSolutionSet> entry : map.entrySet()) {
			sb.append(entry);
		}
		return sb.toString();
	}

	public Map<String, NonDominatedSolutionSet> get() {
		return map;
	}

	public List<NonDominatedSolutionSet> get(IProblem problem, IAlgorithm<?> algorithm) {
		List<NonDominatedSolutionSet> result = new ArrayList<>();
		for(String key : map.keySet()) {
			if (key.startsWith(String.format("p%s_a%s_i", problem, algorithm))) {
				result.add(map.get(key));
			}
		}
		return result;
	}

}
