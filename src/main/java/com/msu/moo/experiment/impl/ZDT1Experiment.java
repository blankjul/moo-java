package com.msu.moo.experiment.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.model.variables.DoubleListVariableFactory;
import com.msu.moo.algorithms.moead.MOEADBuilder;
import com.msu.moo.algorithms.nsgaII.NSGAIIBuilder;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.problems.ZDT1;
import com.msu.moo.visualization.AttainmentSurfacePlot;
import com.msu.operators.crossover.SimulatedBinaryCrossover;
import com.msu.operators.mutation.RealMutation;
import com.msu.util.BashExecutor;

public class ZDT1Experiment extends AExperiment {
	
	protected static final double SEED = 0.8;
	
	@Override
	protected void setAlgorithms(List<IAlgorithm> algorithms) {
		DoubleListVariableFactory fac = new DoubleListVariableFactory(30, new double[] { 0d, 1d });
		NSGAIIBuilder builder = new NSGAIIBuilder();
		
		builder
		.setFactory(fac)
		.setCrossover(new SimulatedBinaryCrossover(new double[]{0.0, 1.0}))
		.setMutation(new RealMutation(new Double[] { 0d, 1d }));
		
		algorithms.add(builder.create());
		
		
		MOEADBuilder builder2 = new MOEADBuilder();
		builder2.setFactory(fac).setT(5).setCrossover(new SimulatedBinaryCrossover(new double[]{0.0, 1.0})).setMutation(new RealMutation(new Double[] { 0d, 1d }));
		algorithms.add(builder2.create());
		
	}


	@Override
	protected void setProblems(List<IProblem> problems) {
		ZDT1 problem = new ZDT1();
		problem.setOptimum(calcFront(problem));
		problems.add(problem);
	}

	
	@Override
	protected void initialize() {
		new AttainmentSurfacePlot().setVisibility(true);
	}
	
	
	protected NonDominatedSolutionSet calcFront(IProblem problem) {
		
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		String command = String.format("vendor/nsga2-gnuplot-v1.1.6/nsga2r %s <vendor/nsga2-gnuplot-v1.1.6/input_data/zdt1.in", SEED);

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
