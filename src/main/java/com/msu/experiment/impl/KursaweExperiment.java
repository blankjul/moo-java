package com.msu.experiment.impl;

import java.util.List;

import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.variables.DoubleListVariableFactory;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.algorithms.moead.MOEADBuilder;
import com.msu.moo.algorithms.nsgaII.NSGAIIBuilder;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.moo.visualization.HypervolumeBoxPlot;
import com.msu.moo.visualization.ObjectiveSpacePlot;
import com.msu.operators.crossover.SimulatedBinaryCrossover;
import com.msu.operators.mutation.RealMutation;
import com.msu.util.Range;

public class KursaweExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		
		Range<Double> range = new Range<>(3, -5d, 5d);
		
		DoubleListVariableFactory fac = new DoubleListVariableFactory(range);
		
		MOEADBuilder builder = new MOEADBuilder();
		builder.setPopulationSize(50);
		builder.setFactory(fac).setT(40).setN_r(40).setDelta(0.3).setCrossover(new SimulatedBinaryCrossover(range)).setMutation(new RealMutation(range));
		algorithms.add(builder.create());
		
		NSGAIIBuilder builder2 = new NSGAIIBuilder();
		builder2.setPopulationSize(50);
		builder2.setFactory(fac).setCrossover(new SimulatedBinaryCrossover(range)).setMutation(new RealMutation(range));
		algorithms.add(builder2.create());
		
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
		new ObjectiveSpacePlot();
	}
	

}
