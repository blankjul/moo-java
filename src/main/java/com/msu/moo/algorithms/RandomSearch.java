package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IFactory;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AMultiObjectiveAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;

/**
 * The RandomSearch just creates randomly new instances and evaluates them until
 * there are no evaluations left.
 */
public class RandomSearch<V extends IVariable, P extends IProblem<V>> extends AMultiObjectiveAlgorithm<V, P> {

	// ! variable factory to create new solutions
	protected IFactory<V> factory;

	public RandomSearch(IFactory<V>  factory) {
		this.factory = factory;
	}


	@Override
	public NonDominatedSolutionSet<V> run_(P problem, IEvaluator evaluator, MyRandom rand) {
		NonDominatedSolutionSet<V> set = new NonDominatedSolutionSet<V>();
		while (factory.hasNext() && evaluator.hasNext()) {
			V var = factory.next(rand);
			Solution<V> s = evaluator.evaluate(problem, var);
			set.add(s);
			evaluator.notify(s);
		}
		return set;
	}



}
