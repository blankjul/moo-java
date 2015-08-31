package com.msu.moo.algorithms;

import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

/**
 * This solver iterates as long as the factory is not able to provide a new solution.
 * It is up to the factory to exhaustive the whole search space!
 */
public class ExhaustiveSolver<V extends IVariable, P extends IProblem> extends AbstractAlgorithm<V,P>{


	public ExhaustiveSolver(VariableFactory<V, P> factory) {
		super(factory);
	}

	@Override
	public NonDominatedSolutionSet run_(P problem) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		V var = null;
		while ((var = factory.create(problem)) != null) {
			Solution s = problem.evaluate(var);
			set.add(s);
		}
		return set;
	}



}
