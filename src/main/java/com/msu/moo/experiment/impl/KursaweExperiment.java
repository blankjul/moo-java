package com.msu.moo.experiment.impl;

import java.util.List;

import com.msu.moo.Builder;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.interfaces.IMultiObjectiveAlgorithm;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.RealMutation;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.util.Range;

public class KursaweExperiment extends AMultiObjectiveExperiment<DoubleListVariable, Kursawe> {

	@Override
	protected void setAlgorithms(List<IMultiObjectiveAlgorithm<DoubleListVariable, Kursawe>> algorithms) {

		Range<Double> range = new Range<>(3, -5d, 5d);
		Builder<NSGAII<DoubleListVariable, Kursawe>> nsgaII = new Builder<>(NSGAII.class);
		nsgaII
		.set("probMutation", 0.3)
		.set("populationSize", 50)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new RealMutation(range));
		
		algorithms.add(nsgaII.build());
		
		algorithms.add(new RandomSearch<DoubleListVariable, Kursawe>(new DoubleListVariableFactory(range)));

	}

	@Override
	protected void setProblems(List<Kursawe> problems) {
		problems.add(new Kursawe());
	}


	

}
