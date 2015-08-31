package com.msu.moo.model.interfaces;

import com.msu.moo.model.solution.NonDominatedSolutionSet;

public interface IExperiment {

	public int getIterations();
	public long getMaxEvaluations();
	public NonDominatedSolutionSet getTrueFront();
	public long getSeed();
}
