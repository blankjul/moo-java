package com.msu.moo.experiment.impl;

import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.nsgaII.NSGAII;
import com.msu.moo.algorithms.impl.nsgaII.NSGAIIBinaryTournament;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentCallback;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.util.Builder;
import com.msu.moo.util.Range;

public class KursaweExperiment extends AExperiment<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, Kursawe> {

	@Override
	protected void setAlgorithms(Kursawe problem, List<IAlgorithm<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, Kursawe>> algorithms) {

		Range<Double> range = new Range<>(3, -5d, 5d);
		Builder<NSGAII<DoubleListVariable, Kursawe>> nsgaII = new Builder<>(NSGAII.class);
		nsgaII.set("probMutation", 1.0)
		.set("populationSize", 100)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new PolynomialMutation(range))
		.set("selector", new NSGAIIBinaryTournament<>());

		algorithms.add(nsgaII.build());


	}

	@Override
	protected void setProblems(List<Kursawe> problems) {
		problems.add(new Kursawe());
	}

	@Override
	protected void analyse(ExperimentCallback<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, Kursawe> c) {
		// String prefix = String.format("[%s/%s | %s | %s/%s ]", c.i + 1,
		// problems.size(), c.algorithm, c.k + 1, iterations);
		//System.out.println(String.format("%s in %f s", c.algorithm, c.duration));
		
		/*
		System.out.println("------------------------------");
		System.out.println(c.problem);
		System.out.println(c.algorithm);
		System.out.println(c.k);
		System.out.println("------------------------------");
		
		for (ISolution<?> s : c.result) {
			String str = String.format("%f %f", s.getObjective(0), s.getObjective(1)).replaceAll("\\[|\\]", "");
			System.out.println(str);
			// System.out.println(s);
		}
		System.out.println("------------------------------");
		*/
		
	}

}
