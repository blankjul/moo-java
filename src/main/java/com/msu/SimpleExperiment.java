package com.msu;

import com.msu.model.Evaluator;
import com.msu.moo.algorithms.nsgaII.NSGAII;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.problems.Kursawe;
import com.msu.operators.crossover.SimulatedBinaryCrossover;
import com.msu.operators.mutation.RealMutation;
import com.msu.util.MyRandom;
import com.msu.util.Range;

public class SimpleExperiment {
	
	public static void main(String[] args) {
		
		Range<Double> range = new Range<>(3, -5d, 5d);
		Builder<NSGAII<DoubleListVariable, Kursawe>> nsgaII = new Builder<>(NSGAII.class);
		nsgaII
		.set("probMutation", 0.3)
		.set("populationSize", 50)
		.set("factory", new DoubleListVariableFactory<Kursawe>(range))
		.set("crossover", new SimulatedBinaryCrossover(range))
		.set("mutation", new RealMutation(range));
		
		NonDominatedSolutionSet<DoubleListVariable> set = nsgaII.build().run(new Kursawe(), new Evaluator<>(50000), new MyRandom());
		
		for (Solution<DoubleListVariable> solution : set) {
			System.out.println(String.format("%f,%f", solution.getObjective(0), solution.getObjective(1)));
		}
		
	}

}
