package com.msu.moo.experiment.impl;

import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.nsgaII.NSGAII;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.callbacks.MultiObjectiveCallback;
import com.msu.moo.interfaces.ICallback;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.util.Builder;
import com.msu.moo.util.Range;

public class KursaweExperiment extends AExperiment<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, Kursawe> {


	
	@Override
	protected void setAlgorithms(Kursawe problem,
			List<IAlgorithm<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, Kursawe>> algorithms) {

		Range<Double> range = new Range<>(3, -5d, 5d);
		Builder<NSGAII<DoubleListVariable, Kursawe>> nsgaII = new Builder<>(NSGAII.class);
		nsgaII.set("probMutation", 0.9)
		.set("populationSize", 100)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new PolynomialMutation(range));

		algorithms.add(nsgaII.build());

		//algorithms.add(new RandomSearch<DoubleListVariable, Kursawe>(new DoubleListVariableFactory(range)));

	}

	@Override
	protected void setProblems(List<Kursawe> problems) {
		problems.add(new Kursawe());
	}


	@Override
	protected ICallback<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, Kursawe> getCallback() {
		return new MultiObjectiveCallback<>();
	}

}
