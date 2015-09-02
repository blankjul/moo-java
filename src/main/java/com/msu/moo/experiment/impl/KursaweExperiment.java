package com.msu.moo.experiment.impl;

import java.util.List;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.experiment.MultiObjectiveExperiment;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;

public class KursaweExperiment extends MultiObjectiveExperiment<Kursawe> {

	@Override
	protected void setAlgorithms() {
		DoubleListVariableFactory<Kursawe> fac = new DoubleListVariableFactory<Kursawe>(3, new double[] { -5, 5 });
		RandomSearch<DoubleListVariable, Kursawe> s = new RandomSearch<>(fac);
		NSGAII<DoubleListVariable, Kursawe> nsgaII = new NSGAII<DoubleListVariable, Kursawe>(fac, new SinglePointCrossover<List<Double>>(),
				new PolynomialMutation(new Double[] { -5.0, 5.0 }));
		algorithms.add(s);
		algorithms.add(nsgaII);
	}

	@Override
	protected void setProblem() {
		this.problem = new Kursawe();
	}
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		KursaweExperiment exp = new KursaweExperiment();
		exp.run(50000, 10, 12337657);
		exp.visualize();
	}

	

}
