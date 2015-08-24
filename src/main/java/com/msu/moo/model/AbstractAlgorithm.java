package com.msu.moo.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.fonseca.EmpiricalAttainmentFunction;
import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;


public abstract class AbstractAlgorithm<V extends IVariable, P extends IProblem<V>> implements IAlgorithm<P> {
	
	//!This method has to be implemented by all algorithms
	protected abstract NonDominatedSolutionSet run_(P problem);

	//! factory for producing new variables
	protected VariableFactory<V, P> factory = null;
	
	//! maximal number of evaluations
	protected long maxEvaluations = 10000L;
	
	//! name for this algorithm
	protected String name = getClass().getSimpleName();
	
	
	public AbstractAlgorithm(VariableFactory<V, P> factory) {
		super();
		this.factory = factory;
	}
	
	public NonDominatedSolutionSet run(P problem) {
		problem.reset();
		return run_(problem);
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

	@Override
	public void setMaxEvaluations(long n) {
		this.maxEvaluations = n;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
}
