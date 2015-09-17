package com.msu.moo.experiment;

import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public abstract class OneProblemNAlgorithmExperiment<P extends IProblem> extends NProblemNAlgorithmExperiment<P> {

	// return the problem instance
	protected abstract P getProblem();

	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

	@Override
	protected void setProblems(ExperimetSettings<P> settings) {
		settings.addProblem(getProblem());
	}

	@Override
	protected void setTrueFronts(ExperimetSettings<P> settings) {
		List<P> problems = settings.getProblems();
		if (problems.size() != 1)
			throw new RuntimeException("More or less than one problem but OneProblemNAlgorithmExperiment Experiment.");
		settings.addTrueFront(problems.get(0), getTrueFront());
	}

}
