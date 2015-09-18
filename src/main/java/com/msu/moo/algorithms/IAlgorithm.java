package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;

/**
 * This interface provides all methods which should be implemented by any
 * algorithm. Basically, the algorithm has to provide a run method to be
 * executed and a name method which describes the algorithm.
 *
 * @param
 * 			<P>
 *            Type of problem instance. This is n
 */
public interface IAlgorithm<P extends IProblem, S> {

	/**
	 * Execute the implementation of the algorithm.
	 * 
	 * @param problem
	 *            instance which should be solved
	 * @param maxEvaluations
	 *            for calculating the front
	 * @return S any result that should be calculated (Solution, Front, ....)
	 */
	public S run(Evaluator<P> problem);

	
	/**
	 * Every algorithm should have a name which is used to execute the toStrin()
	 * method.
	 * 
	 * @param name
	 *            for this algorithm instance
	 */
	public void setName(String name);

}
