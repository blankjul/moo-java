package com.msu.moo.experiment;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.moo.visualization.HypervolumeBoxPlot;

public abstract class AMultiObjectiveExperiment<P extends IProblem> extends AExperiment<P, NonDominatedSolutionSet>{

	@Override
	public void visualize() {
		new AttainmentSurfacePlot<P>(true).show(settings, result);
		if (settings.getAlgorithms().size() > 1) new HypervolumeBoxPlot<P>().show(settings, result);
	}
	
	
}
