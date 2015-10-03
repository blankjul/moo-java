package com.msu.moo.interfaces;

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
public interface IAlgorithm {

	
	/**
	 * Execute the implementation of the algorithm.
	 * @param evaluator which counts the evaluations
	 * @return Front of non dominated points
	 */
	public NonDominatedSolutionSet run(IEvaluator evaluator);

	public NonDominatedSolutionSet run(IProblem problem);
	
	/**
	 * Every algorithm should have a name which is used to execute the toStrin()
	 * method.
	 * 
	 * @param name
	 *            for this algorithm instance
	 */
	public void setName(String name);

}
