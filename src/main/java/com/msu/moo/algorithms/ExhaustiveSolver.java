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

	//! the final result set
	protected NonDominatedSolutionSet set;
	
	//! how many evaluations per next
	protected int evalsPerNext = 100;
	
	protected boolean hasFinished = false;
	
	
	public ExhaustiveSolver(VariableFactory<V, P> factory) {
		super(factory);
		maxEvaluations = Long.MAX_VALUE;
	}


	@Override
	protected void initialize() {
		set = new NonDominatedSolutionSet();
	}

	@Override
	protected void next() {
		if (hasFinished) return;
		for (int i = 0; i < evalsPerNext; i++) {
			V var = factory.create(problem);
			if (var == null) {
				hasFinished = true;
				return;
			}
			Solution s = problem.evaluate(var);
			set.add(s);
		}
	}

	@Override
	protected NonDominatedSolutionSet getResult() {
		return set;
	}


	@Override
	public boolean hasFinished() {
		return hasFinished;
	}
	
	
	



}
