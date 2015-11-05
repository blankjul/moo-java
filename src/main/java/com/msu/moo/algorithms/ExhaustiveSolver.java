package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Random;

/**
 * ExhaustiveSolver ignores the maxEvaluations completely and tries to find new
 * solutions until the factory is not able to create a new solution.
 * 
 * The Factory needs to be design as a exhaustive factory as well, to iterate
 * over all solutions.
 */
public class ExhaustiveSolver extends RandomSearch {

	public ExhaustiveSolver(IVariableFactory factory) {
		super(factory);
	}

	@Override
	public NonDominatedSolutionSet run_(IEvaluator evaluator, Random rand) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (factory.hasNext()) {
			IVariable var = factory.next(evaluator.getProblem(), rand);
			Solution s = evaluator.evaluate(var);
			set.add(s);
		}
		return set;
	}

}
