package com.msu.moo.experiment;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.Solution;

public abstract class ASingleObjectiveExperiment<V extends IVariable, P extends IProblem<V>> extends AExperiment<Solution<V>, V, P>{

	@Override
	protected void analyze(P problem, IAlgorithm<Solution<V>, V, P> algorithm, int run, Solution<V> result) {
		System.out.println(String.format("%s %s %s %s", problem, algorithm, run, result));
	}


}