package com.msu.moo.experiment;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public abstract class AMultiObjectiveExperiment<V extends IVariable, P extends IProblem<V>>
		extends AExperiment<NonDominatedSolutionSet<V>, V, P> {

	@Override
	protected void analyze(P problem, IAlgorithm<NonDominatedSolutionSet<V>, V, P> algorithm, int run,
			NonDominatedSolutionSet<V> result) {

		System.out.println("------------------------------");
		System.out.println(problem);
		System.out.println(algorithm);
		System.out.println(run);
		System.out.println("------------------------------");
		for (Solution<V> s : result) {
			String str = String.format("%f %f", s.getObjective(0), s.getObjective(1)).replaceAll("\\[|\\]", "");
			System.out.println(str);
			// System.out.println(s);
		}
		System.out.println("------------------------------");
	}

}