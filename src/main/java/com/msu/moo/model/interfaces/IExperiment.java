package com.msu.moo.model.interfaces;

import java.util.List;
import java.util.Map;

import com.msu.moo.model.solution.NonDominatedSolutionSet;

public interface IExperiment {

	public int getIterations();
	public long getMaxEvaluations();
	public String getPathToEAF();
	public String getPathToHV();
	public <P extends IProblem> Map<IProblem, NonDominatedSolutionSet> getTrueFronts(List<P> problems);
	
}
