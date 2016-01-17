package com.msu.moo;

import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.RealMutation;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Range;

public class SimpleExperiment {
	
	public static void main(String[] args) {
		
		Range<Double> range = new Range<>(3, -5d, 5d);
		Builder<NSGAII<DoubleListVariable, Kursawe>> builder = new Builder<>(NSGAII.class);
		builder
		.set("probMutation", 0.3)
		.set("populationSize", 50)
		.set("factory", new DoubleListVariableFactory(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new RealMutation(range));
		
		NSGAII<DoubleListVariable, Kursawe> nsgaII = builder.build();
		
		NonDominatedSolutionSet<DoubleListVariable> set = nsgaII.run(new Kursawe(), new Evaluator(50000), new MyRandom());
		
		for (Solution<DoubleListVariable> solution : set) {
			System.out.println(String.format("%f,%f", solution.getObjective(0), solution.getObjective(1)));
		}
		
	}

}
