package com.msu.experiment.impl;

import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.variables.DoubleListVariableFactory;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.moo.visualization.HypervolumeBoxPlot;
import com.msu.moo.visualization.ObjectiveSpacePlot;
import com.msu.operators.OperatorFactory;
import com.msu.operators.crossover.SimulatedBinaryCrossover;
import com.msu.operators.mutation.RealMutation;
import com.msu.util.Range;

public class KursaweExperiment extends AExperiment {

	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		Range<Double> range = new Range<>(3, -5d, 5d);
		DoubleListVariableFactory fac = new DoubleListVariableFactory(range);


		Builder<NSGAII> nsgaII = new Builder<>(NSGAII.class);
		nsgaII
		.set("probMutation", 0.3)
		.set("populationSize", 50)
		.set("fFactory", new OperatorFactory<>(fac))
		.set("fCrossover", new OperatorFactory<>(new SimulatedBinaryCrossover(range)))
		.set("fMutation", new OperatorFactory<>(new RealMutation(range)));
		algorithms.add(nsgaII.build());
		
		algorithms.add(new RandomSearch(new OperatorFactory<>(fac)));

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
