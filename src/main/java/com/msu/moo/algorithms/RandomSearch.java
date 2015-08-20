package com.msu.moo.algorithms;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.NonDominatedSet;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;

public class RandomSearch<V extends IVariable, P extends IProblem<V,P>> implements IAlgorithm<V,P>{

	//! factory for producing new variables
	protected IFactory<V> factory = null;
	
	//! maximal number of evaluations
	protected long maxEvaluations;
	

	@Override
	public NonDominatedSet run(P problem) {
		NonDominatedSet set = new NonDominatedSet();
		Evaluator<V, P> eval = problem.getEvaluator();
		while (maxEvaluations < problem.getEvaluator().count()) {
			set.add(eval.run(problem, factory.create()));
		}
		return set;
	}



}
