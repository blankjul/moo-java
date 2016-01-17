package com.msu.moo.fonseca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Util;

public class EmpiricalAttainmentFunction {

	// ! default path to the EAF executable
	protected String pathToEaf = null;


	public EmpiricalAttainmentFunction(String pathToEaf) {
		this.pathToEaf = pathToEaf;
	}

	public <T> NonDominatedSolutionSet<T> calculate(Collection<NonDominatedSolutionSet<T>> sets) {
		return calculate(sets, (sets.size() / 2) + 1);
	}

	public <T> NonDominatedSolutionSet<T> calculate(Collection<NonDominatedSolutionSet<T>> sets, int level) {

		if (!Util.doesFileExist(pathToEaf)) throw new RuntimeException("Fonseca's Implementation not found!");
		
		// result where the value are added
		NonDominatedSolutionSet<T> result = new NonDominatedSolutionSet<T>();
		String command = getCommand(sets, level);
		String out = BashExecutor.execute(command);
		
		if (out == null) {
			System.out.println(command);
			return null;
		}

		// for each line at the results
		for (String line : out.split("\n")) {

			if (line.startsWith("#"))
				continue;

			// parse the objective values from the line
			List<Double> objectives = new ArrayList<>();
			for (String value : line.split("\\s"))
				objectives.add(Double.valueOf(value));

			result.add(new Solution<T>(null, objectives));

		}

		return result;
	}

	protected <T> String getCommand(Collection<NonDominatedSolutionSet<T>> sets, int level) {
		StringBuilder sb = new StringBuilder();
		sb.append("echo -e \"");
		for (NonDominatedSolutionSet<T> set : sets) {
			sb.append(FonsecaUtil.toString(set.getSolutions()));
			sb.append("\n");
		}
		sb.append("\" | ");
		sb.append(pathToEaf + " -l " + level);
		return sb.toString();
	}

}
