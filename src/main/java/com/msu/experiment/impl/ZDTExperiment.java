package com.msu.experiment.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.msu.builder.MOEADBuilder;
import com.msu.builder.NSGAIIBuilder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.variables.DoubleListVariableFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.problems.DoubleVariableListProblem;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.operators.crossover.SimulatedBinaryCrossover;
import com.msu.operators.mutation.RealMutation;
import com.msu.util.BashExecutor;
import com.msu.util.ObjectFactory;
import com.msu.util.Range;

public class ZDTExperiment extends AExperiment {

	
	final public DoubleVariableListProblem problem = 
			ObjectFactory.create(AbstractZDT.class, "com.msu.moo.problems.ZDT.ZDT1");

	
	final public boolean SHOW_ORIGINAL_NSGAII_FRONT = false;
	
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		Range<Double> range = problem.getVariableConstraints();
		DoubleListVariableFactory fac = new DoubleListVariableFactory(range);
	
	
		NSGAIIBuilder nsgaII = new NSGAIIBuilder();
		nsgaII
				.set("populationSize", 50)
				.set("factory", fac)
				.set("crossover", new SimulatedBinaryCrossover(range))
				.set("mutation", new RealMutation(range));
		algorithms.add(nsgaII.build());
		
	
		
		MOEADBuilder moead = new MOEADBuilder();
		moead
			.set("populationSize", 50)
			.set("factory", fac)
			.set("crossover", new SimulatedBinaryCrossover(range))
			.set("mutation", new RealMutation(range))
			.set("T", 10)
	     	.set("delta", 0.3);
		
		algorithms.add(moead.build());
		
	}
	

	@Override
	protected void initialize() {
		new AttainmentSurfacePlot(SHOW_ORIGINAL_NSGAII_FRONT).setVisibility(true);
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		if (SHOW_ORIGINAL_NSGAII_FRONT) problem.setOptimum(calcFront(problem));
		problems.add(problem);
	}

	
	
	protected NonDominatedSolutionSet calcFront(IProblem problem) {

		String name = problem.toString().toLowerCase();
		
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
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
			result.add(new Solution(null, objectives));

		}
		BashExecutor.execute("rm *.out");
		return result;
	}

}
