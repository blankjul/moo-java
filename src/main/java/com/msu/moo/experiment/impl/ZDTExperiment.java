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
import com.msu.moo.operators.mutation.SimulatedBinaryMutation;
import com.msu.moo.problems.DoubleVariableListProblem;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.problems.ZDT.ZDT1;
import com.msu.moo.util.Builder;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Range;

public class ZDTExperiment extends AExperiment<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT> {


	final public DoubleVariableListProblem problem = 
			ObjectFactory.create(AbstractZDT.class, "com.msu.moo.problems.ZDT.ZDT1");

	
	final public boolean SHOW_ORIGINAL_NSGAII_FRONT = false;


	@Override
	protected void setAlgorithms(AbstractZDT problem,
			List<IAlgorithm<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT>> algorithms) {
	
		Range<Double> range = problem.getVariableConstraints();

		Builder<NSGAII<DoubleListVariable, AbstractZDT>> nsgaII = new Builder<>(NSGAII.class);
		nsgaII
		.set("probMutation", 0.3)
		.set("populationSize", 100)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new SimulatedBinaryMutation(range));
		
		algorithms.add(nsgaII.build());
		
	}
	
	@Override
	protected ICallback<NonDominatedSolutionSet<DoubleListVariable>, DoubleListVariable, AbstractZDT> getCallback() {
		return new MultiObjectiveCallback<>();
	}

	
	@Override
	protected void setProblems(List<AbstractZDT> problems) {
		//if (SHOW_ORIGINAL_NSGAII_FRONT) problem.setOptimum(calcFront(problem));
		problems.add(new ZDT1());
	}

	



}
