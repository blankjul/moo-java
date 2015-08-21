package com.msu.moo.study;

import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.variables.DoubleListVariable;
import com.msu.moo.model.variables.DoubleListVariableFactory;
import com.msu.moo.problems.Kursawe;

public class KursaweStudy {

	public static void main(String[] args) {

		Kursawe k = new Kursawe();
		RandomSearch<DoubleListVariable, Kursawe> s = new RandomSearch<>(
				new DoubleListVariableFactory(3, new double[] { -5, 5 }), 10000L);
		NonDominatedSolutionSet set = s.run(k);
		System.out.println(set);

	}

}
