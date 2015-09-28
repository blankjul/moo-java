package com.msu.moo.report;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IReport;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class SolutionSetReport implements IReport {


	@Override
	public void print(AExperiment experiment) {
		for (IProblem problem : experiment.getProblems()) {
			for (IAlgorithm algorithm : experiment.getAlgorithms()) {
				int counter = 0;
				for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
					System.out.println("---------------------------------------------");
					System.out.println(String.format("Problem: %s | Algorithm: | %s | Run: %s ", problem, algorithm, counter++));
					System.out.println("---------------------------------------------");
					System.out.println(set);
				}
			}
		}
	}

}
