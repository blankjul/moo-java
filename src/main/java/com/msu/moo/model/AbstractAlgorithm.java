package com.msu.moo.model;

import java.util.Map;
import java.util.TreeMap;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public abstract class AbstractAlgorithm<V extends IVariable, P extends IProblem> implements IAlgorithm<P> {


	// ! initialize the algorithm
	protected abstract void initialize();

	// ! execute the next iteration step
	protected abstract void next();
	
	//! return the front as a result -> should be available every generation
	protected abstract NonDominatedSolutionSet getResult();

	//! the current problem
	protected P problem = null;

	// ! factory for producing new variables
	protected VariableFactory<V, P> factory = null;

	// ! maximal number of evaluations
	protected long maxEvaluations = 50000L;

	// ! name for this algorithm
	protected String name = getClass().getSimpleName();

	// ! all fronts during the time
	protected boolean recordHistory = true;
	protected Map<Long, NonDominatedSolutionSet> history = new TreeMap<>();

	public AbstractAlgorithm(VariableFactory<V, P> factory) {
		super();
		this.factory = factory;
	}

	public NonDominatedSolutionSet run(P problem) {
		this.problem = problem;
		this.problem.reset();
		
		initialize();
		
		while (!hasFinished()) {
			next();
			if (recordHistory) history.put(problem.getNumOfEvaluations(), getResult());
		}
		
		return getResult();
	}
	
	public boolean hasFinished() {
		return maxEvaluations <= problem.getNumOfEvaluations();
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

	public Map<Long, NonDominatedSolutionSet> getHistory() {
		return history;
	}
	
	
	
	

}
