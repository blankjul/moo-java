package com.msu.moo.model;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.SolutionSet;

public class AlgorithmState {

	
	protected String name;
	
	protected IEvaluator evaluator;
	
	protected SolutionSet<? extends ISolution<?>> solutions;
	
	
	
	public AlgorithmState(String name, IEvaluator evaluator, SolutionSet<? extends ISolution<?>> solutions) {
		super();
		this.name = name;
		this.evaluator = evaluator;
		this.solutions = solutions;
	}

	public String getName() {
		return name;
	}

	public IEvaluator getEvaluator() {
		return evaluator;
	}

	public SolutionSet<? extends ISolution<?>> getSolutionSet() {
		return solutions;
	}

}
