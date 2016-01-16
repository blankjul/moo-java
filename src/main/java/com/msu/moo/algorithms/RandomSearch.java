package com.msu.moo.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.AMultiObjectiveAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.util.MyRandom;

/**
 * The RandomSearch just creates randomly new instances and evaluates them until
 * there are no evaluations left.
 */
public class RandomSearch<V extends IVariable, P extends IProblem<V>> extends AMultiObjectiveAlgorithm<V, P> {

	// ! variable factory to create new solutions
	protected IFactory<V> factory;

	public RandomSearch(IFactory<V>  fFactory) {
		this.factory = fFactory;
	}


	@Override
	public NonDominatedSolutionSet<V> run_(P problem, IEvaluator<V, P> evaluator, MyRandom rand) {
		NonDominatedSolutionSet<V> set = new NonDominatedSolutionSet<V>();
		while (factory.hasNext() && evaluator.hasNext()) {
			V var = factory.next(rand);
			Solution<V> s = evaluator.evaluate(problem, var);
			set.add(s);
		}
		return set;
	}



}
