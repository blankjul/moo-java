package com.msu.moo.model;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;


public abstract class AbstractAlgorithm<V extends IVariable, P extends IProblem> implements IAlgorithm<P> {
	
	//!This method has to be implemented by all algorithms
	protected abstract NonDominatedSolutionSet run_(P problem);

	//! factory for producing new variables
	protected VariableFactory<V, P> factory = null;
	
	//! maximal number of evaluations
	protected long maxEvaluations = 50000L;
	
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
	
	@Override
	public String toString() {
		return name;
	}

	public VariableFactory<V, P> getFactory() {
		return factory;
	}

	public void setFactory(VariableFactory<V, P> factory) {
		this.factory = factory;
	}

	public long getMaxEvaluations() {
		return maxEvaluations;
	}
	
	
	
	
	
	
	
	
}
