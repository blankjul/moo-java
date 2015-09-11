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
	
	//! the final result set
	protected NonDominatedSolutionSet set;
	
	//! how many evaluations per next
	protected int evalsPerNext = 100;
	

	@Override
	protected void initialize() {
		set = new NonDominatedSolutionSet();
	}

	@Override
	protected void next() {
		for (int i = 0; i < evalsPerNext; i++) {
			V var = factory.create(problem);
			Solution s = problem.evaluate(var);
			set.add(s);
		}
	}

	@Override
	protected NonDominatedSolutionSet getResult() {
		return new NonDominatedSolutionSet(set);
	}



}
