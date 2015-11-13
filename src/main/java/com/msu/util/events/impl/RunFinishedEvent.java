package com.msu.util.events.impl;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.events.IEvent;

public class RunFinishedEvent implements IEvent {

	protected AExperiment experiment;

	protected IProblem problem;

	protected IAlgorithm algorithm;
	
	protected int n;
	
	protected NonDominatedSolutionSet set;


	public RunFinishedEvent(AExperiment experiment, IProblem problem, IAlgorithm algorithm, int n, NonDominatedSolutionSet set) {
		super();
		this.experiment = experiment;
		this.problem = problem;
		this.algorithm = algorithm;
		this.n = n;
		this.set = set;
	}

	public AExperiment getExperiment() {
		return experiment;
	}

	public IProblem getProblem() {
		return problem;
	}

	public IAlgorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(IAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	public int getRun() {
		return n;
	}

	public void setRun(int n) {
		this.n = n;
	}

	public NonDominatedSolutionSet getNonDominatedSolutionSet() {
		return set;
	}

	public void setNonDominatedSolutionSet(NonDominatedSolutionSet set) {
		this.set = set;
	}

	public void setExperiment(AExperiment experiment) {
		this.experiment = experiment;
	}

	public void setProblem(IProblem problem) {
		this.problem = problem;
	}
	
	

}
