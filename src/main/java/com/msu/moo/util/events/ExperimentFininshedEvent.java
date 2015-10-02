package com.msu.moo.util.events;

import com.msu.moo.experiment.AExperiment;

public class ExperimentFininshedEvent implements IEvent {

	protected AExperiment experiment;

	public ExperimentFininshedEvent(AExperiment experiment) {
		super();
		this.experiment = experiment;
	}

	public AExperiment getExperiment() {
		return experiment;
	}
	
	
}
