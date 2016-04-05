package com.msu.moo.experiment.impl;

import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.builder.NSGAIIBuilder;
import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentCallback;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.operators.selection.RandomSelection;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.problems.ZDT.ZDT1;
import com.msu.moo.util.IListener;
import com.msu.moo.util.Range;

public class NSGAIIExperiment extends AExperiment<NonDominatedSet<ISolution<?>>, DoubleListVariable, AbstractZDT> {

	class HVListener implements IListener<SolutionSet<NSGAIISolution<DoubleListVariable>>> {

		protected String nameOfAlgorithm = null;

		public HVListener(String nameOfAlgorithm) {
			super();
			this.nameOfAlgorithm = nameOfAlgorithm;
		}

		int counter = 0;

		@Override
		public void notify(SolutionSet<NSGAIISolution<DoubleListVariable>> population) {
			final double hv = Hypervolume.calculate(population);
			System.out.println(String.format("%s,%s,%f", nameOfAlgorithm, counter++, hv));

		}

	}

	@Override
	protected void setAlgorithms(AbstractZDT problem, List<IAlgorithm<NonDominatedSet<ISolution<?>>, DoubleListVariable, AbstractZDT>> algorithms) {

		Range<Double> range = problem.getRange();

		NSGAIIBuilder<DoubleListVariable, AbstractZDT> builder = new NSGAIIBuilder<>();
		builder
		.set("name", "BinaryTournament")
		.set("probMutation", 1.0)
		.set("populationSize", 100)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new PolynomialMutation(range))
		.set("listener", new HVListener("BinaryTournament"));

		algorithms.add(builder.build());

		builder
			.set("selector", new RandomSelection<>())
			.set("name", "RandomSelection")
			.set("listener", new HVListener("RandomSelection"));
		algorithms.add(builder.build());

	}

	@Override
	protected void setProblems(List<AbstractZDT> problems) {
		problems.add(new ZDT1());
	}

	@Override
	protected void analyse(ExperimentCallback<NonDominatedSet<ISolution<?>>, DoubleListVariable, AbstractZDT> callback) {
	}

}
