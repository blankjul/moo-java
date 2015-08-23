package com.msu.moo.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;


public abstract class Algorithm<V extends IVariable, P extends IProblem<V,P>> implements IAlgorithm<P> {
	

	//! factory for producing new variables
	protected IFactory<V> factory = null;
	
	//! maximal number of evaluations
	protected long maxEvaluations;
	
	public Algorithm(IFactory<V> factory, long maxEvaluations) {
		super();
		this.factory = factory;
		this.maxEvaluations = maxEvaluations;
	}
	
	public NonDominatedSolutionSet run(P problem, int n) {
		List<NonDominatedSolutionSet> sets = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			NonDominatedSolutionSet s = run(problem);
			sets.add(s);
		}
		NonDominatedSolutionSet result = new EmpiricalAttainmentFunction().calculate(sets);
		return result;
	}
	
}
