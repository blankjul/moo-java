package com.msu.moo.study;

import java.util.List;

import com.msu.moo.algorithms.NSGAII;
import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.mutation.PolynomialMutation;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.visualization.ScatterPlot;

public class KursaweStudy {

	public static void main(String[] args) {

		Kursawe k = new Kursawe();
		
		
		RandomSearch<DoubleListVariable, Kursawe> s = new RandomSearch<>(
				new DoubleListVariableFactory(3, new double[] { -5, 5 }), 1000000L);
		NonDominatedSolutionSet set = s.run(k);
		System.out.println(set);

		System.out.println("----------------");
		
		
		SinglePointCrossover<List<Double>> spc = new SinglePointCrossover<List<Double>>();
		PolynomialMutation sm = new PolynomialMutation(new Double[]{-5.0,5.0});
		
		NSGAII<DoubleListVariable, Kursawe> nsgaII = new NSGAII<DoubleListVariable, Kursawe>(
				new DoubleListVariableFactory(3, new double[] { -5, 5 }), 1000000L, spc, sm);
		NonDominatedSolutionSet set2 = nsgaII.run(k);
		System.out.println(set2);
		
		ScatterPlot sp = new ScatterPlot("Kursawe", "X", "Y");
		sp.add(set.getSolutions(), "RANDOM");
		sp.add(set2.getSolutions(), "NSGAII");
		sp.show();
		
	}
	

}
