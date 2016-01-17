package com.msu.moo.interfaces.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.MyRandom;

/**
 * This class represents an multi-objective algorithm which provides a non
 * dominated solution set.
 *
 * @param <E>
 *            encoded type of the variable which is used.
 */
public interface IMultiObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> extends IAlgorithm<NonDominatedSolutionSet<V>, V, P>{

	
	/**
	 * Execute the implementation of the algorithm.
	 * 
	 * @param problem
	 *            to solve
	 * @param evaluator
	 *            which counts the evaluations
	 * @return front of non dominated points
	 */
	public  NonDominatedSolutionSet<V> run(P problem, IEvaluator evaluator, MyRandom rand);

	
	
}
