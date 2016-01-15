package com.msu.moo.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.util.MyRandom;

/**
 * ExhaustiveSolver ignores the maxEvaluations completely and tries to find new
 * solutions until the factory is not able to create a new solution.
 * 
 * The Factory needs to be design as a exhaustive factory as well, to iterate
 * over all solutions.
 */
public class ExhaustiveSolver<V extends IVariable, P extends IProblem<V>> extends RandomSearch<V,P> {

	public ExhaustiveSolver(IFactory<V, P>  factory) {
		super(factory);
	}
	
	@Override
	public NonDominatedSolutionSet<V> run_(P problem, IEvaluator<V,P> evaluator, MyRandom rand) {
		NonDominatedSolutionSet<V> set = new NonDominatedSolutionSet<V>();
		while (factory.hasNext()) {
			V var = factory.next(problem, rand);
			// here: use problem evaluator directly, because it is an exhaustive search!
			Solution<V> s = problem.evaluate(var);
			set.add(s);
		}
		return set;
	}

}
