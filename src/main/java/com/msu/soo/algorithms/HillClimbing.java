package com.msu.soo.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.interfaces.IVariableFactory;
import com.msu.moo.model.solution.Solution;
import com.msu.operators.AbstractMutation;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.util.MyRandom;

public class HillClimbing extends ASingleObjectiveAlgorithm {

	protected IVariable var = null;
	protected IVariableFactory factory = null;

	protected AbstractMutation<?> mutation = null;

	public HillClimbing(IVariable var, AbstractMutation<?> mutation) {
		this.var = var;
		this.mutation = mutation;
	}

	public HillClimbing(IVariableFactory factory, AbstractMutation<?> mutation) {
		this.factory = factory;
		this.mutation = mutation;
	}

	public Solution initialize(IProblem problem, IEvaluator eval, MyRandom rand) {
		Solution s = null;
		if (var == null) {
			s =  eval.evaluate(problem, factory.next(problem, rand));
		} else {
			s =  eval.evaluate(problem, var);
		}
		var = null;
		return s;
	}

	@Override
	public Solution run__(IProblem problem, IEvaluator eval, MyRandom rand) {

		Solution s = initialize(problem, eval, rand);

		while (eval.hasNext()) {

			IVariable nextVar = mutation.mutate(s.getVariable().copy(), problem, rand);
			Solution next = eval.evaluate(problem, nextVar);

			if (next.getSumOfConstraintViolation() < s.getSumOfConstraintViolation()) {
				s = next;
			} else if (next.getSumOfConstraintViolation().equals(s.getSumOfConstraintViolation())) {
				if (next.getObjectives(0) < s.getObjectives(0)) {
					s = next;
				}
			}
		}
		

		return s;

	}

}
