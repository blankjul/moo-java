package com.msu.interfaces;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.util.MyRandom;

/**
 * This class represents an multi-objective algorithm which provides a non
 * dominated solution set.
 *
 * @param <E>
 *            encoded type of the variable which is used.
 */
public interface IMultiObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> {

	
	/**
	 * Execute the implementation of the algorithm.
	 * 
	 * @param problem
	 *            to solve
	 * @param evaluator
	 *            which counts the evaluations
	 */
	public IMultiObjectiveAlgorithm<V,P> initialize(P problem, IEvaluator<V,P> evaluator, MyRandom rand);

	
	/**
	 * @return front of non dominated points
	 */
	public  NonDominatedSolutionSet<V> run();

	
	
}
