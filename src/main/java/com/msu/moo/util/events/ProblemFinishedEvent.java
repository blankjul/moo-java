package com.msu.moo.util.events;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IProblem;

public class ProblemFinishedEvent implements IEvent {

	protected AExperiment experiment;
	
	protected IProblem problem;


	public ProblemFinishedEvent(AExperiment experiment, IProblem problem) {
		super();
		this.experiment = experiment;
		this.problem = problem;
	}

	public AExperiment getExperiment() {
		return experiment;
	}

	public IProblem getProblem() {
		return problem;
	}
	
	
	
}
