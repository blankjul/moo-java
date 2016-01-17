package com.msu.moo.interfaces;

import com.msu.moo.model.solution.Solution;

public interface ILocalOptimization<V extends IVariable, P extends IProblem<V>> {

	public Solution<V> run(P problem, IEvaluator evaluator, V var);
	
}
