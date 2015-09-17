package com.msu.moo.experiment.impl;

import java.util.List;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.experiment.OneProblemNAlgorithmExperiment;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;

public class KursaweExperiment extends OneProblemNAlgorithmExperiment<Kursawe> {

	@Override
	protected Kursawe getProblem() {
		return new Kursawe();
	}


	@Override
	protected void setAlgorithms(ExperimetSettings<Kursawe> settings) {
		
		DoubleListVariableFactory<Kursawe> fac = new DoubleListVariableFactory<Kursawe>(3, new double[] { -5, 5 });
		NSGAIIBuilder<DoubleListVariable, Kursawe> builder = new NSGAIIBuilder<>();
		builder .setFactory(fac)
				.setCrossover(new SinglePointCrossover<List<Double>>())
				.setMutation(new PolynomialMutation(new Double[] { -5.0, 5.0 }));
		settings.addAlgorithm(builder.create());
		
		
		settings.addAlgorithm(new RandomSearch<>(fac));
		
	}


	

}
