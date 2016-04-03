package com.msu.moo;

import java.util.Arrays;

import com.msu.moo.algorithms.impl.nsgaII.NSGAII;
import com.msu.moo.algorithms.impl.nsgaII.NSGAIIBinaryTournament;
import com.msu.moo.fonseca.Hypervolume;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.model.variable.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SimulatedBinaryCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.DoubleVariableListProblem;
import com.msu.moo.util.Builder;
import com.msu.moo.util.IListener;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.ObjectFactory;
import com.msu.moo.util.Range;
import com.msu.moo.util.visualization.NonDominatedSetToJson;

public class SimpleExperiment {
	
	
	final public static String PROBLEM = "com.msu.moo.problems.Kursawe";
	
	
	public static void main(String[] args) {
		
		final NonDominatedSetToJson json = new NonDominatedSetToJson();
		
		IListener<SolutionSet<ISolution<DoubleListVariable>>> listener = new IListener<SolutionSet<ISolution<DoubleListVariable>>>() {
			@Override
			public void notify(SolutionSet<ISolution<DoubleListVariable>> var) {
				json.append(var);
			}
		};
		
		
		DoubleVariableListProblem problem =  ObjectFactory.create(DoubleVariableListProblem.class, PROBLEM);
		
		Range<Double> range = problem.getVariableConstraints();
		
		Builder<NSGAII<DoubleListVariable, DoubleVariableListProblem>> builder = new Builder<>(NSGAII.class);
		builder
			.set("probMutation", 1.0)
			.set("populationSize", 100)
			.set("factory", new DoubleListVariableFactory(range))
			.set("crossover", new SimulatedBinaryCrossover(range))
			.set("mutation", new PolynomialMutation(range))
			.set("listener", listener)
		    .set("selector", new NSGAIIBinaryTournament<>());
		
		
		
		NSGAII<DoubleListVariable, DoubleVariableListProblem> nsgaII = builder.buildNoClone();
		
		NonDominatedSet<ISolution<DoubleListVariable>> set = nsgaII.run(problem, new StandardEvaluator(20000), new MyRandom(123456789));
		
		System.out.println(Hypervolume.calculate(set.getSolutions(),Arrays.asList(1.01, 1.01)));
		
		for (ISolution<DoubleListVariable> solution : set) {
			System.out.println(String.format("%f,%f -> %s", solution.getObjective(0), solution.getObjective(1), solution.getVariable()));
		}
		
		json.asHtml(String.format("%s.html", PROBLEM));
		
	}

}
