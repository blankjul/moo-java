package com.msu.moo.experiment.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.RealMutation;
import com.msu.moo.problems.DoubleVariableListProblem;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.problems.ZDT.ZDT1;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.util.Builder;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Range;

public class ZDTExperiment extends AMultiObjectiveExperiment<DoubleListVariable, AbstractZDT> {

	
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
		.set("populationSize", 50)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new RealMutation(range));
		
		algorithms.add(nsgaII.build());
		
	}

	
	@Override
	protected void setProblems(List<AbstractZDT> problems) {
		//if (SHOW_ORIGINAL_NSGAII_FRONT) problem.setOptimum(calcFront(problem));
		problems.add(new ZDT1());
	}

	
	
	protected NonDominatedSolutionSet<DoubleListVariable> calcFront(AbstractZDT problem) {

		String name = problem.toString().toLowerCase();
		
		NonDominatedSolutionSet<DoubleListVariable> result = new NonDominatedSolutionSet<>();
		String command = String
				.format("vendor/nsga2-gnuplot-v1.1.6/%s %s <vendor/nsga2-gnuplot-v1.1.6/input_data/%s.in", name, 0.8, name);

		BashExecutor.execute(command);
		FileInputStream fis = null;

		try {
			fis = new FileInputStream("plot.out");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String out = BashExecutor.fromStream(fis);

		// for each line at the results
		for (String line : out.split("\n")) {

			if (line.startsWith("#"))
				continue;

			// parse the objective values from the line
			List<Double> objectives = new ArrayList<>();
			String[] values = line.split("\\t");
			objectives.add(Double.valueOf(values[0]));
			objectives.add(Double.valueOf(values[1]));
			result.add(new Solution<DoubleListVariable>(null, objectives));

		}
		BashExecutor.execute("rm *.out");
		return result;
	}










}
