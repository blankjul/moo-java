package com.msu.moo.experiment.impl;

import java.util.List;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.moo.visualization.HypervolumeBoxPlot;
import com.msu.moo.visualization.ObjectiveSpacePlot;

public class KursaweExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		DoubleListVariableFactory fac = new DoubleListVariableFactory(3, new double[] { -5, 5 });
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setFactory(fac).setCrossover(new SinglePointCrossover<List<Double>>()).setMutation(new PolynomialMutation(new Double[] { -5.0, 5.0 }));
		algorithms.add(builder.create());
		algorithms.add(new RandomSearch(fac));
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		problems.add(new Kursawe());
	}

	@Override
	protected void initialize() {
		new AttainmentSurfacePlot().setVisibility(true);
		new HypervolumeBoxPlot().setVisibility(true);
		new ObjectiveSpacePlot().setOutputFolder(".");
	}
	

}
