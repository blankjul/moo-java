package com.msu.experiment.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.builder.Builder;
import com.msu.experiment.AExperiment;
import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.variables.DoubleListVariableFactory;
import com.msu.moo.algorithms.DecomposedAlgorithm;
import com.msu.moo.algorithms.moead.MOEADBuilder;
import com.msu.moo.algorithms.nsgaII.NSGAIIBuilder;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.problems.DoubleVariableListProblem;
import com.msu.moo.problems.ZDT.AbstractZDT;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.operators.crossover.SimulatedBinaryCrossover;
import com.msu.operators.mutation.RealMutation;
import com.msu.soo.algorithms.HillClimbing;
import com.msu.util.BashExecutor;
import com.msu.util.ObjectFactory;

public class ZDTExperiment extends AExperiment {

	
	final public DoubleVariableListProblem problem = 
			ObjectFactory.create(AbstractZDT.class, "com.msu.moo.problems.ZDT.ZDT1");

	
	final public boolean SHOW_ORIGINAL_NSGAII_FRONT = true;
	
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {

		DoubleListVariableFactory fac = new DoubleListVariableFactory(problem.getVariableConstraints());
		NSGAIIBuilder builder = new NSGAIIBuilder();
		builder.setPopulationSize(100);
		builder.setFactory(fac).setCrossover(new SimulatedBinaryCrossover(problem.getVariableConstraints()))
				.setMutation(new RealMutation(problem.getVariableConstraints()));
		algorithms.add(builder.create());
		
		
		MOEADBuilder builder2 = new MOEADBuilder();
		builder2.setPopulationSize(100);
		builder2.setDelta(20);
		builder2.setFactory(fac).setCrossover(new SimulatedBinaryCrossover(problem.getVariableConstraints()))
				.setMutation(new RealMutation(problem.getVariableConstraints()));
		algorithms.add(builder2.create());
		
		
		Builder<DecomposedAlgorithm> b = new Builder<DecomposedAlgorithm>(new DecomposedAlgorithm());
		b.set("numOfWeights", 100);
		b.set("algorithms",Arrays.asList(new HillClimbing(fac, new RealMutation(problem.getVariableConstraints()))));
		algorithms.add(b.build());
		
	}
	

	@Override
	protected void initialize() {
		new AttainmentSurfacePlot(SHOW_ORIGINAL_NSGAII_FRONT).setVisibility(true);
	}

	@Override
	protected void setProblems(List<IProblem> problems) {
		problem.setOptimum(calcFront(problem));
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
