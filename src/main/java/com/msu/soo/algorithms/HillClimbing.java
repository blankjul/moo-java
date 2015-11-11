package com.msu.soo.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.model.AbstractAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Random;

public class HillClimbing extends AbstractAlgorithm{

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



	@Override
	public NonDominatedSolutionSet run_(IEvaluator eval, Random rand) {
		
		Solution s = null;
		if (var == null) {
			s = eval.evaluate(factory.next(eval.getProblem(), rand));
		} else {
			s = eval.evaluate(var);
		}
		
		if (s.getObjective().size() != 1) throw new RuntimeException("Only for single-objectives problems!");
		
		while (eval.hasNext()) {
			
			IVariable nextVar = mutation.mutate(s.getVariable().copy(), rand);
			Solution next = eval.evaluate(nextVar);
			
			s = new Evaluator(eval.getProblem()).evaluate(s.getVariable());
			
			if (next.getSumOfConstraintViolation() < s.getSumOfConstraintViolation()) {
				s = next;
			}
			if (next.getSumOfConstraintViolation().equals(s.getSumOfConstraintViolation())) {
				if (next.getObjectives(0) < s.getObjectives(0)) {
					s = next;
				}
			}
			
		}
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(s);
		return set;
		
	}

}
