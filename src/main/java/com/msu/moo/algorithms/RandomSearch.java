package com.msu.moo.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.OperatorFactory;
import com.msu.util.MyRandom;

/**
 * The RandomSearch just creates randomly new instances and evaluates them until
 * there are no evaluations left.
 */
public class RandomSearch extends AbstractAlgorithm {

	// ! variable factory to create new solutions
	protected OperatorFactory<IFactory> fFactory;

	public RandomSearch(OperatorFactory<IFactory> fFactory) {
		this.fFactory = fFactory;
	}

	@Override
	public NonDominatedSolutionSet run_(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		IFactory fac = fFactory.create(problem, rand, evaluator);
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (fac.hasNext() && evaluator.hasNext()) {
			IVariable var = fac.next();
			Solution s = evaluator.evaluate(problem, var);
			set.add(s);
		}
		return set;
	}


}
