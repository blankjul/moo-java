package com.msu.moo.model.evaluator;

import java.util.List;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

public class ConvergenceEvaluator extends AEvaluator {

	
	//! how often the same result has to be notified to converge
	protected int convergencePeriod = 50;
	
	//! only first N solutions of notifications are observed
	protected int firstN = -1;
	
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
	
	
	
	public ConvergenceEvaluator(int convergencePeriod, int firstN) {
		super();
		this.convergencePeriod = convergencePeriod;
		this.firstN = firstN;
	}


	public <V extends IVariable> Solution<V> evaluate(IProblem<? extends IVariable> problem, V variable) {
		return super.evaluate(problem, variable);
	}

	@Override
	public boolean hasNext() {
		return counter <= convergencePeriod;
	}

	
	@Override
	public <S extends ISolution<V>, V extends IVariable> void notify(SolutionSet<S,V> set) {
		
		List<S> list = set;
		if (firstN != -1 && set.size() >  firstN) list = set.subList(0, firstN);
		
		final int hash = list.hashCode();
		if (lastHashCode == hash) ++counter;
		else {
			lastHashCode = hash;
			counter = 1;
		}
	}

	
	@Override
	public Integer getMaxEvaluations() {
		return Integer.MAX_VALUE;
	}
	
	

}
