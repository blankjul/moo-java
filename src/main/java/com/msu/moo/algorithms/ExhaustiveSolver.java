package com.msu.moo.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.OperatorFactory;
import com.msu.util.MyRandom;

/**
 * ExhaustiveSolver ignores the maxEvaluations completely and tries to find new
 * solutions until the factory is not able to create a new solution.
 * 
 * The Factory needs to be design as a exhaustive factory as well, to iterate
 * over all solutions.
 */
public class ExhaustiveSolver extends RandomSearch {


	public ExhaustiveSolver(OperatorFactory<IFactory> fFactory) {
		super(fFactory);
	}
	@Override
	public NonDominatedSolutionSet run_(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		IFactory factory = fFactory.create(problem, rand, evaluator);
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (factory.hasNext()) {
			IVariable var = factory.next();
			// here: use problem evaluator directly, because it is an exhaustive search!
			Solution s = problem.evaluate(var);
			set.add(s);
		}
		return set;
	}

}
