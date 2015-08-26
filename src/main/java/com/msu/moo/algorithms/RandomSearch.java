package com.msu.moo.algorithms;

import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public class RandomSearch<V extends IVariable, P extends IProblem> extends AbstractAlgorithm<V,P>{


	public RandomSearch(VariableFactory<V, P> factory) {
		super(factory);
	}

	@Override
	public NonDominatedSolutionSet run_(P problem) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		while (maxEvaluations > problem.getNumOfEvaluations()) {
			
			// create a new variable
			V var = factory.create(problem);
			Solution s = problem.evaluate(var);
			
			set.add(s);
		}
		return set;
	}



}
