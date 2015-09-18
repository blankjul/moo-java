package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.interfaces.IProblem;

public class ExperimetSettings<P extends IProblem, S> {

	protected List<P> problems = null;
	protected List<IAlgorithm<S,P>> algorithms = null;
	protected Map<P, S> mOptima = null;
	
	
	public ExperimetSettings() {
		this.problems = new ArrayList<>();
		this.algorithms = new ArrayList<>();
		this.mOptima = new HashMap<>();
	}


	public ExperimetSettings(List<P> problems, List<IAlgorithm<S,P>> algorithms, Map<P, S> mOptima) {
		this.problems = problems;
		this.algorithms = algorithms;
		this.mOptima = mOptima;
	}

	
	public void addAlgorithm(IAlgorithm<S,P> algorithm) {
		algorithms.add(algorithm);
	}
	
	public void addProblem(P problem) {
		problems.add(problem);
	}
	
	public void addOptima(P problem, S optima) {
		mOptima.put(problem, optima);
	}

	public List<P> getProblems() {
		return problems;
	}


	public void setProblems(List<P> problems) {
		this.problems = problems;
	}


	public List<IAlgorithm<S,P>> getAlgorithms() {
		return algorithms;
	}


	public void setAlgorithms(List<IAlgorithm<S,P>> algorithms) {
		this.algorithms = algorithms;
	}


	public Map<P, S> getOptima() {
		return mOptima;
	}


	public void setOptima(Map<P, S> mFronts) {
		this.mOptima = mFronts;
	}
	
	
	
	
	
}
