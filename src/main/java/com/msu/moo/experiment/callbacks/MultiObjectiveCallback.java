package com.msu.moo.experiment.callbacks;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.interfaces.ICallback;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSet;

public class MultiObjectiveCallback<V extends IVariable, P extends IProblem<V>>
		implements ICallback<NonDominatedSet<ISolution<V>, V>, V, P> {

	@Override
	public void analyze(P problem, IAlgorithm<NonDominatedSet<ISolution<V>, V>, V, P> algorithm, int run,
			NonDominatedSet<ISolution<V>, V> result) {

		System.out.println("------------------------------");
		System.out.println(problem);
		System.out.println(algorithm);
		System.out.println(run);
		System.out.println("------------------------------");
		for (ISolution<V> s : result) {
			String str = String.format("%f %f", s.getObjective(0), s.getObjective(1)).replaceAll("\\[|\\]", "");
			System.out.println(str);
			// System.out.println(s);
		}
		System.out.println("------------------------------");
	}

}