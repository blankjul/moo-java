package com.msu.moo.algorithms.impl;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

/**
 * ExhaustiveSolver ignores the maxEvaluations completely and tries to find new
 * solutions until the factory is not able to create a new solution.
 * 
 * The Factory needs to be design as a exhaustive factory as well, to iterate
 * over all solutions.
 */
public class ExhaustiveSolver<V extends IVariable, P extends IProblem> extends RandomSearch<V,P> {

	public ExhaustiveSolver(IVariableFactory<V, P> factory) {
		super(factory);
	}

	@Override
	public NonDominatedSolutionSet run(Evaluator<P> evaluator) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (factory.hasNext()) {
			V var = factory.next(evaluator.getProblem());
			Solution s = evaluator.evaluate(var);
			set.add(s);
		}
		return set;
	}

}
