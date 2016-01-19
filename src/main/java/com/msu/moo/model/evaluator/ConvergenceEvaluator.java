package com.msu.moo.model.evaluator;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class ConvergenceEvaluator extends AEvaluator {

	
	//! how often the same result has to be notified to converge
	protected int convergencePeriod = 50;
	
	//! hash code of the last notification
	protected int lastHashCode = -1;
	
	//! how often the same result was notified
	protected int counter = 1;
	
	
	public ConvergenceEvaluator() {
		super();
	}
	

	public ConvergenceEvaluator(int convergencePeriod) {
		super();
		this.convergencePeriod = convergencePeriod;
	}
	

	public <V extends IVariable> Solution<V> evaluate(IProblem<? extends IVariable> problem, V variable) {
		return super.evaluate(problem, variable);
	}

	@Override
	public boolean hasNext() {
		return counter <= convergencePeriod;
	}

	
	@Override
	public <V extends IVariable> void notify(SolutionSet<V> set) {
		final int hash = set.hashCode();
		if (lastHashCode == hash) ++counter;
		else {
			lastHashCode = set.hashCode();
			counter = 1;
		}
	}

	
	@Override
	public Integer getMaxEvaluations() {
		return Integer.MAX_VALUE;
	}
	
	

}
