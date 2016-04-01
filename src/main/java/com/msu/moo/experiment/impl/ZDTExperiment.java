package com.msu.moo.experiment.impl;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.nsgaII.NSGAII;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.ICallback;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.problems.ZDT.ZDT3;
import com.msu.moo.util.Builder;
import com.msu.moo.util.Range;

public class ZDTExperiment extends AExperiment<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, AbstractZDT> {



	@Override
	protected void setAlgorithms(AbstractZDT problem,
			List<IAlgorithm<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, AbstractZDT>> algorithms) {
	
		Range<Double> range = problem.getVariableConstraints();

		Builder<NSGAII<DoubleListVariable, AbstractZDT>> nsgaII = new Builder<>(NSGAII.class);
		nsgaII
		.set("probMutation", 1.0)
		.set("populationSize", 100)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new PolynomialMutation(range));
		
		algorithms.add(nsgaII.build());
		
	}
	
	@Override
	protected ICallback<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, AbstractZDT> getCallback() {
		return new ICallback<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, AbstractZDT>() {
			
			@Override
			public void analyze(AbstractZDT problem,
					IAlgorithm<NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable>, DoubleListVariable, AbstractZDT> algorithm, int run,
					NonDominatedSet<ISolution<DoubleListVariable>, DoubleListVariable> result) {
				
				System.out.println(Hypervolume.calculate(result.getSolutions(), Arrays.asList(1.01, 1.01)));
				
			}
		};
		//return new MultiObjectiveCallback<>();
	}

	
	@Override
	protected void setProblems(List<AbstractZDT> problems) {
		problems.add(new ZDT3());
	}



}
