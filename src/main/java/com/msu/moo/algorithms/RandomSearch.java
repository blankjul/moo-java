package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

/**
 * The RandomSearch just creates randomly new instances until there are no evaluations left.
 */
public class RandomSearch<V extends IVariable, P extends IProblem> extends AbstractAlgorithm<V,P>{

	//! variable factory to create new solutions
	protected IVariableFactory<V, P> factory;

	public RandomSearch(IVariableFactory<V, P> factory) {
		this.factory = factory;
	}

	@Override
	public NonDominatedSolutionSet run(P problem, long maxEvaluations) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (factory.hasNext() && problem.getNumOfEvaluations() < maxEvaluations) {
			V var = factory.next(problem);
			Solution s = problem.evaluate(var);
			set.add(s);
		}
		return set;
	}



}
