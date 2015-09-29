package com.msu.moo.report;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IReport;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class SolutionSetReport implements IReport {


	@Override
	public StringBuffer print(AExperiment experiment) {
		StringBuffer sb = new StringBuffer();
		for (IProblem problem : experiment.getProblems()) {
			for (IAlgorithm algorithm : experiment.getAlgorithms()) {
				int counter = 0;
				for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
					sb.append("---------------------------------------------\n");
					sb.append(String.format("Problem: %s | Algorithm: | %s | Run: %s \n", problem, algorithm, counter++));
					sb.append("---------------------------------------------\n");
					sb.append(set.toString());
				}
			}
		}
		return sb;
	}

}
