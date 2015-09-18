package com.msu.moo.algorithms.impl;

import com.msu.moo.algorithms.AMultiObjectiveAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.MultiObjectiveSolution;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

/**
 * The RandomSearch just creates randomly new instances and evaluates them until
 * there are no evaluations left.
 */
public class RandomSearch<V extends IVariable, P extends IProblem> extends AMultiObjectiveAlgorithm<P> {

	// ! variable factory to create new solutions
	protected IVariableFactory<V, P> factory;

	public RandomSearch(IVariableFactory<V, P> factory) {
		this.factory = factory;
	}

	@Override
	public NonDominatedSolutionSet run(Evaluator<P> evaluator) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (factory.hasNext() && evaluator.hasNext()) {
			V var = factory.next(evaluator.getProblem());
			MultiObjectiveSolution s = evaluator.evaluate(var);
			set.add(s);
		}
		return set;
	}

}
