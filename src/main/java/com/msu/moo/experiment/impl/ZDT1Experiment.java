package com.msu.moo.experiment.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.msu.moo.algorithms.impl.NSGAIIBuilder;
import com.msu.moo.experiment.AMultiObjectiveExperiment;
import com.msu.moo.experiment.ExperimetSettings;
import com.msu.moo.model.solution.MultiObjectiveSolution;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.RealMutation;
import com.msu.moo.problems.ZDT1;
import com.msu.moo.util.BashExecutor;

public class ZDT1Experiment extends AMultiObjectiveExperiment<ZDT1>{
	
	
	@Override
	protected void setAlgorithms(ExperimetSettings<ZDT1, NonDominatedSolutionSet> settings) {
		
		DoubleListVariableFactory<ZDT1> fac = new DoubleListVariableFactory<>(30, new double[] { 0d, 1d });
		NSGAIIBuilder<DoubleListVariable, ZDT1> builder = new NSGAIIBuilder<>();
		
		builder
		.setFactory(fac)
		.setCrossover(new SimulatedBinaryCrossover(new double[]{0.0, 1.0}))
		.setMutation(new RealMutation(new Double[] { 0d, 1d }));
		
		settings.addAlgorithm(builder.create());
		
	}


	@Override
	protected void setProblems(ExperimetSettings<ZDT1, NonDominatedSolutionSet> settings) {
		settings.addProblem(new ZDT1());
	}
	


	@Override
	protected void setOptima(ExperimetSettings<ZDT1, NonDominatedSolutionSet> settings) {
		
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
				result.add(new MultiObjectiveSolution(null, objectives));

			}
			BashExecutor.execute("rm *.out");
			settings.addOptima(settings.getProblems().get(0), result);;
	}



	
	
	
}
