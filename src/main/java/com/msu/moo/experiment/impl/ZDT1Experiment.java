package com.msu.moo.experiment.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.algorithms.NSGAIIBuilder;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.RealMutation;
import com.msu.moo.problems.ZDT1;
import com.msu.moo.util.BashExecutor;
import com.msu.moo.visualization.ScatterPlot;

public class ZDT1Experiment {
	
	public static void main(String[] args) {

		DoubleListVariableFactory<ZDT1> fac = new DoubleListVariableFactory<ZDT1>(30, new double[] { 0d, 1d });
		
		NSGAIIBuilder<DoubleListVariable, ZDT1> builder = new NSGAIIBuilder<>();
		builder
		.setFactory(fac)
		.setCrossover(new SinglePointCrossover<>())
		.setMutation(new RealMutation(new Double[] { 0d, 1d }))
		.setMaxEvaluations(20000);
		
		
		ScatterPlot sp = new ScatterPlot("ZDT1", "X", "Y");
		
		
		sp.add(ZDT1Experiment.execute(0.6).getSolutions(), "C Implementation");
		//sp.add(builder.create().run(new ZDT1()).getSolutions(), "My Implementation SPX");
		
		builder.setCrossover(new SimulatedBinaryCrossover(new double[]{0d, 1d}));
		sp.add(builder.create().run(new ZDT1()).getSolutions(), "My Implementation SBX");
		
		
		sp.show();
	}
	
	

	public static NonDominatedSolutionSet execute(double seed) {
		
		if (seed <= 0 || seed >= 1) throw new RuntimeException("Seed is out of bounds!");
		
		NonDominatedSolutionSet result = new NonDominatedSolutionSet();
		String command = String.format("vendor/nsga2-gnuplot-v1.1.6/nsga2r %s <vendor/nsga2-gnuplot-v1.1.6/input_data/zdt1.in", seed);

			BashExecutor.execute(command);
			FileInputStream fis;
			
			try {
				fis = new FileInputStream("plot.out");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
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
