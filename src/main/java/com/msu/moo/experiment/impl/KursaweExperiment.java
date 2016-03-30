package com.msu.moo.experiment.impl;

import java.util.List;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.callback.ICallback;
import com.msu.moo.experiment.callback.MultiObjectiveCallback;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.util.Builder;
import com.msu.moo.util.Range;

public class KursaweExperiment extends AExperiment<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, Kursawe> {


	
	@Override
	protected void setAlgorithms(Kursawe problem,
			List<IAlgorithm<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, Kursawe>> algorithms) {

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
	protected ICallback<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, Kursawe> getCallback() {
		return new MultiObjectiveCallback<>();
	}

}
