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

public class KursaweExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		
		DoubleListVariableFactory fac = new DoubleListVariableFactory(3, new double[] { -5, 5 });
		
		MOEADBuilder builder = new MOEADBuilder();
		builder.setPopulationSize(50);
		builder.setFactory(fac).setT(40).setN_r(40).setDelta(0.3).setCrossover(new SimulatedBinaryCrossover(new double[] { -5, 5 })).setMutation(new RealMutation(new Double[] { -5.0, 5.0 }, 20));
		algorithms.add(builder.create());
		
		NSGAIIBuilder builder2 = new NSGAIIBuilder();
		builder2.setPopulationSize(50);
		builder2.setFactory(fac).setCrossover(new SimulatedBinaryCrossover(new double[] { -5, 5 })).setMutation(new RealMutation(new Double[] { -5.0, 5.0 }));
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
