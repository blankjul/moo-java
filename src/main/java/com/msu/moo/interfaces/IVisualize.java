package com.msu.moo.interfaces;

import com.msu.moo.experiment.ExperimentResult;
import com.msu.moo.experiment.ExperimetSettings;

/**
 * This interface provides the method to visualize an experiment result in any
 * way. 
 */
public interface IVisualize<P extends IProblem> {

	public void show(ExperimetSettings<P> settings, ExperimentResult result);

}
