package com.msu.moo.model.evaluator;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.exceptions.EvaluationException;

/**
 * The Evaluator class should be used for each algorithm to evaluate the result
 * of an object of type IVariable.
 * 
 * It is not forbidden to use the problem itself directly, but the evaluator is
 * counting the evaluations and also some other feature like hashing results
 * might be implemented.
 *
 */
public abstract class AEvaluator implements IEvaluator {

	// ! current amount of evaluations
	private int evaluations = 0;

	// ! current amount of evaluations
	protected IEvaluator father = null;
	

	@SuppressWarnings("unchecked")
	@Override
	public <V extends IVariable> Solution<V> evaluate(IProblem<? extends IVariable> problem, V variable) {

		// increase the number of evaluations
		increase();

		// cast the problem to the specific one
		IProblem<V> p = null;
		try {
			p = (IProblem<V>) problem;
		} catch (Exception e) {
			throw new EvaluationException("Problem does not accept the given variable!");
		}
		return p.evaluate(variable);
	}

	public int getEvaluations() {
		return evaluations;
	}

	public void increase() {
		++this.evaluations;
		if (father != null)
			father.increase();
	}
	


	@Override
	public Integer numOfEvaluations() {
		return evaluations;
	}

	@Override
	public <S extends ISolution<V>, V extends IVariable> void notify(S s) {
		SolutionSet<S,V> set = new SolutionSet<>();
		set.add(s);
		notify(set);
	}

	@Override
	public void setFather(IEvaluator father) {
		this.father = father;
	}
	
	@Override
	public <S extends ISolution<V>, V extends IVariable> void notify(SolutionSet<S, V> set) {
	}

}
