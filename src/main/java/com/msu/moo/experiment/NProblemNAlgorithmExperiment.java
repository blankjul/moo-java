package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.moo.visualization.HypervolumeBoxPlot;

public abstract class NProblemNAlgorithmExperiment<P extends IProblem> extends AbstractExperiment<P>{


	@Override
	protected Collection<IVisualize<P>> getVisualizer() {
		List<IVisualize<P>> visualizer =  new ArrayList<IVisualize<P>>();
		visualizer.add(new AttainmentSurfacePlot<P>(true));
		visualizer.add(new HypervolumeBoxPlot<P>());
		return visualizer;
	}



}
