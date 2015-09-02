package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.visualization.BoxPlot;

public abstract class SingeĺeObjectiveExperiment<P extends IProblem> extends AbstractExperiment<P> {

	@Override
	public void visualize() {

		if (fronts == null)
			throw new RuntimeException("No fronts available. Run the experiment before visualizing it!");
		
			BoxPlot bp = new BoxPlot(problem.toString());
			for (IAlgorithm<P> algorithm : fronts.keySet()) {
				List<Double> values = new ArrayList<>();
				List<NonDominatedSolutionSet> sets = fronts.get(algorithm);
				for(NonDominatedSolutionSet set : sets) {
					if (set.size() != 1) throw new RuntimeException("The size of the set at SOP has to be one!");
					values.add(set.getSolutions().get(0).getObjectives().get(0));
				}
				bp.add(values, algorithm.toString());
				System.out.println(String.format("%s,%s", values, algorithm));
			}
			bp.show();
		
		
		
		
	}

}
