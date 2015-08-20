package com.msu.moo.study;

import com.msu.moo.algorithms.RandomSearch;
import com.msu.moo.model.NonDominatedSet;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.variables.DoubleListVariable;
import com.msu.moo.variables.DoubleListVariableFactory;

public class KursaweStudy {

	public static void main(String[] args) {

		Kursawe k = new Kursawe();
		RandomSearch<DoubleListVariable, Kursawe> s = new RandomSearch<>(
				new DoubleListVariableFactory(3, new double[] { -5, 5 }), 10000000L);
		NonDominatedSet set = s.run(k);
		System.out.println(set);

	}

}
