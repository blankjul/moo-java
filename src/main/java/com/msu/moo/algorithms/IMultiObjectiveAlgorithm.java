package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.util.MyRandom;

/**
 * This class represents an multi-objective algorithm which provides a non
 * dominated solution set.
 *
 * @param <E>
 *            encoded type of the variable which is used.
 */
public interface IMultiObjectiveAlgorithm<V extends IVariable, P extends IProblem<V>> extends IAlgorithm<NonDominatedSet<ISolution<V>>, V, P>{

	
	/**
	 * Execute the implementation of the algorithm.
	 * 
	 * @param problem
	 *            to solve
	 * @param evaluator
	 *            which counts the evaluations
	 * @return front of non dominated points
	 */
	public  NonDominatedSet<ISolution<V>> run(P problem, IEvaluator evaluator, MyRandom rand);

	
	
}
