package com.msu.moo.algorithms;

import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.AbstractEvaluator;
import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public class RandomSearch<V extends IVariable, P extends IProblem<V,P>> extends AbstractAlgorithm<V,P>{


	public RandomSearch(IFactory<V> factory, long maxEvaluations) {
		super(factory, maxEvaluations);
	}

	@Override
	public NonDominatedSolutionSet run(P problem) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		AbstractEvaluator<V, P> eval = problem.getEvaluator();
		while (maxEvaluations > eval.count()) {
			
			// create a new variable
			V var = factory.create();
			Solution s = eval.run(var);
			
			set.add(s);
		}
		return set;
	}



}
