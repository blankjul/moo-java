package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.MyRandom;

/**
 * This class represents an single objective algorithm which provides a solution
 * to a single objective problem.
 *
 * @param <E>
 *            encoded type of the variable which is used.
 */
public interface ISingleObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> extends IAlgorithm<ISolution<V>, V, P>{

	/**
	 * Execute the implementation of the algorithm.
	 * 
	 * @param problem
	 *            to solve
	 * @param evaluator
	 *            which counts the evaluations
	 * @return best found solution for the problem
	 */
	public ISolution<V> run(P problem, IEvaluator evaluator, MyRandom rand);

}
