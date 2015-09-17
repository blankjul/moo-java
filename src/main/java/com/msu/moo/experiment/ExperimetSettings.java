package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class ExperimetSettings<P extends IProblem> {

	protected List<P> problems = null;
	protected List<IAlgorithm<P>> algorithms = null;
	protected Map<P, NonDominatedSolutionSet> mFronts = null;
	
	
	public ExperimetSettings() {
		this.problems = new ArrayList<>();
		this.algorithms = new ArrayList<>();
		this.mFronts = new HashMap<>();
	}


	public ExperimetSettings(List<P> problems, List<IAlgorithm<P>> algorithms, Map<P, NonDominatedSolutionSet> mFronts) {
		this.problems = problems;
		this.algorithms = algorithms;
		this.mFronts = mFronts;
	}

	
	public void addAlgorithm(IAlgorithm<P> algorithm) {
		algorithms.add(algorithm);
	}
	
	public void addProblem(P problem) {
		problems.add(problem);
	}
	
	public void addTrueFront(P problem, NonDominatedSolutionSet trueFront) {
		mFronts.put(problem, trueFront);
	}

	public List<P> getProblems() {
		return problems;
	}


	public void setProblems(List<P> problems) {
		this.problems = problems;
	}


	public List<IAlgorithm<P>> getAlgorithms() {
		return algorithms;
	}


	public void setAlgorithms(List<IAlgorithm<P>> algorithms) {
		this.algorithms = algorithms;
	}


	public Map<P, NonDominatedSolutionSet> getmFronts() {
		return mFronts;
	}


	public void setmFronts(Map<P, NonDominatedSolutionSet> mFronts) {
		this.mFronts = mFronts;
	}
	
	
	
	
	
}
