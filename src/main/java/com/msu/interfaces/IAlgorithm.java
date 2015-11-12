package com.msu.interfaces;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

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

	public NonDominatedSolutionSet run(IEvaluator evaluator);
	
	
	/**
	 * Execute the implementation of the algorithm.
	 * @param evaluator which counts the evaluations
	 * @return Front of non dominated points
	 */
	public NonDominatedSolutionSet run(IEvaluator evaluator, Random rand);
	
	

	
	/**
	 * Every algorithm should have a name which is used to execute the toStrin()
	 * method.
	 * 
	 * @param name
	 *            for this algorithm instance
	 */
	public void setName(String name);

}
