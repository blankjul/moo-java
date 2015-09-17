package com.msu.moo.interfaces;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

/**
 * This interface provides all methods which should be implemented by any
 * algorithm. Basically, the algorithm has to provide a run method to be
 * executed and a name method which describes the algorithm.
 *
 * @param
 * 			<P>
 *            Type of problem instance. This is n
 */
public interface IAlgorithm<P extends IProblem> {

	/**
	 * Execute the implementation of the algorithm.
	 * 
	 * @param problem
	 *            instance which should be solved
	 * @param maxEvaluations
	 *            for calculating the front
	 * @return NonDominatedSolutionSet
	 */
	public NonDominatedSolutionSet run(Evaluator<P> problem, long maxEvaluations);

	/**
	 * Every algorithm should have a name which is used to execute the toStrin()
	 * method.
	 * 
	 * @param name
	 *            for this algorithm instance
	 */
	public void setName(String name);

}
