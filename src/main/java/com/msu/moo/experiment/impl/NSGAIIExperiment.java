package com.msu.moo.experiment.impl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.builder.NSGAIIBuilder;
import com.msu.moo.algorithms.builder.RandomSearchBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentCallback;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.AlgorithmState;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.operators.selection.RandomSelection;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.problems.ZDT.ZDT1;
import com.msu.moo.util.IListener;

public class NSGAIIExperiment extends AExperiment<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, AbstractZDT> {


	private static final StringBuffer sb = new StringBuffer();
	
	
	public NSGAIIExperiment() throws FileNotFoundException {
		super();
		sb.append("algorithm,generation,hv\n");
	}

	
	@Override
	protected void setAlgorithms(AbstractZDT problem, List<IAlgorithm<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, AbstractZDT>> algorithms) {
		
		
		NSGAIIBuilder<DoubleListVariable, AbstractZDT> builder = new NSGAIIBuilder<>();
		builder
		.set("name", "BinaryTournament")
		.set("probMutation", 1.0)
		.set("populationSize", 100)
		.set("factory", new DoubleListVariableFactory(problem.getRange()))
		.set("crossover", new SimulatedBinaryCrossover(problem.getRange()))
		.set("mutation", new PolynomialMutation(problem.getRange()))
		.set("listener", new IListener<AlgorithmState>() {
			@Override
			public void notify(AlgorithmState state) {
				int generation = state.getEvaluator().numOfEvaluations() / 100;
				Double hv = Hypervolume.calculate(state.getSolutionSet());
				sb.append(String.format("%s,%s,%f\n", state.getName(),generation , hv));
			}
		});

		algorithms.add(builder.build());

		builder
			.set("selector", new RandomSelection<>())
			.set("name", "RandomSelection");
		algorithms.add(builder.build());
		
		algorithms.add(new RandomSearchBuilder<DoubleListVariable, AbstractZDT>()
				.set("listener", new IListener<AlgorithmState>() {
					@Override
					public void notify(AlgorithmState state) {
						int generation = state.getEvaluator().numOfEvaluations() / 100;
						double hv = Hypervolume.calculate(state.getSolutionSet());
						sb.append(String.format("%s,%s,%f\n", state.getName(),generation , hv));
					}})
				.set("factory", new DoubleListVariableFactory(problem.getRange())).build());

	}

	@Override
	protected void setProblems(List<AbstractZDT> problems) {
		problems.add(new ZDT1());
	}

	@Override
	protected void analyse(ExperimentCallback<NonDominatedSet<ISolution<DoubleListVariable>>, DoubleListVariable, AbstractZDT> callback) {
	}

	@Override
	public void finalize() {
		PrintWriter pw;
		try {
			pw = new PrintWriter("test.csv");
			pw.append(sb.toString());
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


	}

	
	
	
	
	
	

}
