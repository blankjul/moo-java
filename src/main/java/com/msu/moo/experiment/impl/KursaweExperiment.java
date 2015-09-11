package com.msu.moo.experiment.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.experiment.OneProblemNAlgorithmExperiment;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;

public class KursaweExperiment extends OneProblemNAlgorithmExperiment<Kursawe> {


	public static void main(String[] args) {
		BasicConfigurator.configure();
		KursaweExperiment exp = new KursaweExperiment();
		exp.run(50000, 10, 1234);
		exp.report();
	}

	@Override
	protected Kursawe getProblem() {
		return new Kursawe();
	}

	@Override
	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

	@Override
	protected List<IAlgorithm<Kursawe>> getAlgorithms() {
		List<IAlgorithm<Kursawe>> result= new ArrayList<>();
		
		DoubleListVariableFactory<Kursawe> fac = new DoubleListVariableFactory<Kursawe>(3, new double[] { -5, 5 });
		NSGAII<DoubleListVariable, Kursawe> nsgaII = new NSGAII<DoubleListVariable, Kursawe>(fac, new SinglePointCrossover<List<Double>>(),
				new PolynomialMutation(new Double[] { -5.0, 5.0 }));
		result.add(nsgaII);
		
		RandomSearch<DoubleListVariable, Kursawe> s = new RandomSearch<>(fac);
		result.add(s);
		
		return result;
	}

	

}
