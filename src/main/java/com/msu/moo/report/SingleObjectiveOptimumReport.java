package com.msu.moo.report;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IReport;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class SingleObjectiveOptimumReport implements IReport {

	//! objective that is printed - indeed single objective but
	//! if first objective always zero second objective is interesting
	protected int objective = 0;
	
	
	public SingleObjectiveOptimumReport() {
		super();
	}


	public SingleObjectiveOptimumReport(int objective) {
		super();
		this.objective = objective;
	}



	@Override
	public StringBuffer print(AExperiment experiment) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%s,%s,%s,%d\n", "problem", "optimum"));
		for (IProblem problem : experiment.getProblems()) {
			NonDominatedSolutionSet optima = problem.getOptimum();
			if(optima != null) {
				sb.append(String.format("%s,%s\n", problem, optima.get(0).getObjectives(objective)));
			}
		}
		return sb;
	}

}
