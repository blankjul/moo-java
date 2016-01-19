package com.msu.moo.interfaces;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;

/**
 * This interface templates the evaluators that are used to evaluate the
 * solution for a specific problem. The evaluator counts the evaluations and
 * could determine through hasNext() if evaluations are left.
 * 
 * @param <E>
 *            encoded variable
 */
public interface IEvaluator {

	
	/**
	 * Evaluate the variable using the problem
	 * 
	 * @return always MultiObjectiveSolution but could also have one objective
	 */
	public <V extends IVariable> Solution<V> evaluate(IProblem<? extends IVariable> problem, V variable);

	/**
	 * @return whether further evaluations are allowed or not
	 */
	public boolean hasNext();

	/**
	 * Each Evaluator should have a maximal number of available evaluations.
	 * 
	 * @return maximal allowed evaluations
	 */
	public Integer getMaxEvaluations();

	
	/**
	 * Create child evaluator that counts also this one.
	 */
	public void setFather(IEvaluator father);

	
	/**
	 * @return current number of used evaluations.
	 */
	public Integer numOfEvaluations();
	
	/**
	 * increase the counting by one.
	 */
	public void increase();
	
	
	/**
	 * @param set current best non dominated solution set
	 */
	public <V extends IVariable> void notify(SolutionSet<V> set);
	
	
	/**
	 * @param set current best solution
	 */
	public <V extends IVariable> void notify(Solution<V> s);
	
	
	
	
	
}
