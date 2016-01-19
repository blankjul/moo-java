package com.msu.moo.experiment.callback;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.solution.Solution;

public class SingleObjectiveCallback<V extends IVariable, P extends IProblem<V>> implements ICallback<Solution<V>, V, P>{

	@Override
	public void analyze(P problem, IAlgorithm<Solution<V>, V, P> algorithm, int run, Solution<V> result) {
		System.out.println(String.format("%s,%s,%s,%s", problem, algorithm, run, result));
	}


}