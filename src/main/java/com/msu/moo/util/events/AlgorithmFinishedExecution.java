package com.msu.moo.util.events;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;

public class AlgorithmFinishedExecution implements IEvent {

	protected AExperiment experiment;

	protected IProblem problem;

	protected IAlgorithm algorithm;

	public AlgorithmFinishedExecution(AExperiment experiment, IProblem problem, IAlgorithm algorithm) {
		super();
		this.experiment = experiment;
		this.problem = problem;
		this.algorithm = algorithm;
	}

	public AExperiment getExperiment() {
		return experiment;
	}

	public IProblem getProblem() {
		return problem;
	}

}
