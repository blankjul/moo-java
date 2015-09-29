package com.msu.moo.experiment.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.RealMutation;
import com.msu.moo.problems.ZDT1;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.visualization.AttainmentSurfacePlot;

public class ZDT1Experiment extends AExperiment {
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		DoubleListVariableFactory fac = new DoubleListVariableFactory(30, new double[] { 0d, 1d });
		NSGAIIBuilder builder = new NSGAIIBuilder();
		
		builder
		.setFactory(fac)
		.setCrossover(new SimulatedBinaryCrossover(new double[]{0.0, 1.0}))
		.setMutation(new RealMutation(new Double[] { 0d, 1d }));
		
		algorithms.add(builder.create());
	}


	@Override
	protected void setProblems(List<IProblem> problems) {
		ZDT1 problem = new ZDT1();
		problem.setOptimum(calcFront(problem));
		problems.add(new ZDT1());
	}

	
	@Override
	protected void finalize() {
		new AttainmentSurfacePlot().show(this);
	}

	
	
	
	
	protected NonDominatedSolutionSet calcFront(IProblem problem) {
		
		double seed = 0.8;
		if (seed <= 0 || seed >= 1) throw new RuntimeException("Seed is out of bounds!");
		
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		String command = String.format("vendor/nsga2-gnuplot-v1.1.6/nsga2r %s <vendor/nsga2-gnuplot-v1.1.6/input_data/zdt1.in", seed);

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
