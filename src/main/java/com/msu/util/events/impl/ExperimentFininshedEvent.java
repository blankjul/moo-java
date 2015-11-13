package com.msu.util.events.impl;

import com.msu.experiment.AExperiment;
import com.msu.util.events.IEvent;

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
