package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.model.AbstractExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;

public class KursaweExperiment extends AbstractExperiment<Kursawe> {

	public KursaweExperiment() {
		this.maxEvaluations = 100000L;
		this.iterations = 5;
	}

	public static void main(String[] args) {
		new KursaweExperiment().run();
	}

	@Override
	protected List<IAlgorithm<Kursawe>> getAlgorithms() {
		List<IAlgorithm<Kursawe>> algorithms = new ArrayList<>();

		DoubleListVariableFactory<Kursawe> fac = new DoubleListVariableFactory<Kursawe>(3, new double[] { -5, 5 });

		RandomSearch<DoubleListVariable, Kursawe> s = new RandomSearch<>(fac);
		NSGAII<DoubleListVariable, Kursawe> nsgaII = new NSGAII<DoubleListVariable, Kursawe>(fac,
				new SinglePointCrossover<List<Double>>(), new PolynomialMutation(new Double[] { -5.0, 5.0 }));

		algorithms.add(s);
		algorithms.add(nsgaII);

		return algorithms;
	}

	@Override
	protected List<Kursawe> getProblems() {
		return new ArrayList<Kursawe>(Arrays.asList(new Kursawe()));
	}

}
