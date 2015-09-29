package com.msu.moo.report;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IReport;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public class SingleObjectiveReport implements IReport {

	//! objective that is printed - indeed single objective but
	//! if first objective always zero second objective is interesting
	protected int objective = 0;
	
	
	public SingleObjectiveReport() {
		super();
	}


	public SingleObjectiveReport(int objective) {
		super();
		this.objective = objective;
	}



	@Override
	public StringBuffer print(AExperiment experiment) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s,%s,%s,%s\n", "problem", "algorithm", "objective", "variable"));
		for (IProblem problem : experiment.getProblems()) {
			for (IAlgorithm algorithm : experiment.getAlgorithms()) {
				for (NonDominatedSolutionSet set : experiment.getResult().get(problem, algorithm)) {
					if (set.size() != 1)
						throw new RuntimeException("Single Objective problem only one solution allowed.");
					Solution best = set.get(0);
					sb.append(String.format("%s,%s,%s,\"%s\"\n", problem, algorithm, best.getObjectives(objective), best.getVariable()));
				}
			}
		}
		return sb;
	}

}
