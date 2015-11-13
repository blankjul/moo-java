package com.msu.interfaces;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.Random;

public interface IAlgorithm {

	
	/**
	 * Every algorithm should have a name which is used to execute the toStrin()
	 * method.
	 * 
	 * @param name
	 *            for this algorithm instance
	 */
	public void setName(String name);

	/**
	 * Execute the implementation of the algorithm.
	 * @param problem to solve
	 * @param evaluator which counts the evaluations
	 * @return Front of non dominated points
	 */
	public NonDominatedSolutionSet run(IProblem problem, IEvaluator evaluator, Random rand);
	
	
	
}
