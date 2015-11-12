package com.msu.moo.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IVariable;
import com.msu.interfaces.IVariableFactory;
import com.msu.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Random;

/**
 * The RandomSearch just creates randomly new instances and evaluates them until
 * there are no evaluations left.
 */
public class RandomSearch extends AbstractAlgorithm {

	// ! variable factory to create new solutions
	protected IVariableFactory factory;

	public RandomSearch(IVariableFactory factory) {
		this.factory = factory;
	}

	@Override
	public NonDominatedSolutionSet run_(IEvaluator evaluator, Random rand) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (factory.hasNext() && evaluator.hasNext()) {
			IVariable var = factory.next(evaluator.getProblem(), rand);
			Solution s = evaluator.evaluate(var);
			set.add(s);
		}
		return set;
	}


}
