package com.msu.moo.fonseca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.msu.moo.Configuration;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Util;

/**
 * 
 *
 * EAF from Fonseca
 * -----------------------------------------------------------------------------
 * The empirical first-order attainment function (EAF) is used to assess the
 * performance of stochastic multiobjective optimisers such as multiobjective
 * evolutionary algorithms
 * 
 * https://eden.dei.uc.pt/~cmfonsec/software.html
 *
 */
public class EmpiricalAttainmentFunction {

	public static NonDominatedSet<ISolution<Object>> calculate(Collection<Collection<? extends ISolution<?>>> sets) {
		return calculate(sets, (sets.size() / 2) + 1);
	}

	public static NonDominatedSet<ISolution<Object>> calculate(Collection<Collection<? extends ISolution<?>>> sets, int level) {

		if (!Util.doesFileExist(Configuration.PATH_TO_EAF))
			throw new RuntimeException("Fonseca's Implementation not found!");

		// result where the value are added
		NonDominatedSet<ISolution<Object>> result = new NonDominatedSet<>();
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

			result.add(new Solution<Object>(null, objectives));

		}

		return result;
	}

	protected static String getCommand(Collection<Collection<? extends ISolution<?>>> sets, int level) {
		StringBuilder sb = new StringBuilder();
		sb.append("echo -e \"");
		for (Collection<? extends ISolution<?>> set : sets) {
			sb.append(FonsecaUtil.toString(set));
			sb.append("\n");
		}
		sb.append("\" | ");
		sb.append(Configuration.PATH_TO_EAF + " -l " + level);
		return sb.toString();
	}

}
