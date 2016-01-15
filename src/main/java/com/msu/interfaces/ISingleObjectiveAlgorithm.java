package com.msu.interfaces;

import com.msu.moo.model.solution.Solution;
import com.msu.util.MyRandom;

/**
 * This class represents an single objective algorithm which provides a solution
 * to a single objective problem.
 *
 * @param <E>
 *            encoded type of the variable which is used.
 */
public interface ISingleObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> {

	/**
	 * Execute the implementation of the algorithm.
	 * 
	 * @param problem
	 *            to solve
	 * @param evaluator
	 *            which counts the evaluations
	 */
	public ISingleObjectiveAlgorithm<V,P> initialize(P problem, IEvaluator<V,P> evaluator, MyRandom rand);

	
	
	/**
	 * @return best found solution for the problem
	 */
	public Solution<V> run();

}
