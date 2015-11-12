package com.msu.moo.util.events.impl;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.util.events.IEvent;

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
