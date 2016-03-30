package com.msu.moo.experiment.impl;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.callback.ICallback;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.problems.ZDT.ZDT3;
import com.msu.moo.util.Builder;
import com.msu.moo.util.Range;

public class ZDTExperiment extends AExperiment<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT> {




	@Override
	protected void setAlgorithms(AbstractZDT problem,
			List<IAlgorithm<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT>> algorithms) {
	
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
	protected ICallback<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT> getCallback() {
		return new ICallback<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT>() {
			
			@Override
			public void analyze(AbstractZDT problem,
					IAlgorithm<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT> algorithm, int run,
					NonDominatedSolutionSet<DoubleListVariable> result) {
				
				System.out.println(Hypervolume.calculate(result.getSolutions(),Arrays.asList(1.01, 1.01)));
				
			}
		};
		//return new MultiObjectiveCallback<>();
	}

	
	@Override
	protected void setProblems(List<AbstractZDT> problems) {
		problems.add(new ZDT3());
	}



}
