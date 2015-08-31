package com.msu.moo.experiment;

import java.util.List;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.model.AbstractExperiment;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;

public class KursaweExperiment extends AbstractExperiment<Kursawe> {


	@Override
	public int getIterations() {
		return 10;
	}

	@Override
	public long getMaxEvaluations() {
		return 100000L;
	}

	
	
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

	@Override
	public long getSeed() {
		return 123;
	}

	
	

}
