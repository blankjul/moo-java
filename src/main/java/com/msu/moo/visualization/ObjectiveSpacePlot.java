package com.msu.moo.visualization;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.AVisualize;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.IListener;
import com.msu.moo.util.events.RunFinishedEvent;
import com.msu.moo.util.plots.ScatterPlot;

public class ObjectiveSpacePlot extends AVisualize implements IListener<RunFinishedEvent>{

	// ! also plot the true front if it exists
	protected boolean showTrueFront = true;

	
	public ObjectiveSpacePlot() {
		super();
		EventDispatcher.getInstance().register(RunFinishedEvent.class, this);
	}

	public ObjectiveSpacePlot(boolean showTrueFront) {
		this();
		this.showTrueFront = showTrueFront;
	}

	@Override
	public void update(RunFinishedEvent event) {
		IProblem problem = event.getProblem();
		ScatterPlot sp = new ScatterPlot(problem.toString(), "X", "Y");
		sp.add(event.getNonDominatedSolutionSet(), event.getAlgorithm().toString());
		NonDominatedSolutionSet front = problem.getOptimum();
		if (showTrueFront &&  front != null) sp.add(front, "TrueFront");
		showOrPrint(sp, String.format("%s-%s-%s-objspace", problem, event.getAlgorithm(), event.getRun()));
	}
}
