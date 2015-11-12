package com.msu.soo.algorithms;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IVariable;
import com.msu.interfaces.IVariableFactory;
import com.msu.model.AbstractAlgorithm;
import com.msu.model.Evaluator;
import com.msu.model.SingleObjectiveDecomposedProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Random;
import com.msu.moo.util.Range;
import com.msu.operators.AbstractMutation;

public class HillClimbing extends AbstractAlgorithm {

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
		
		SingleObjectiveDecomposedProblem<?> decomposed = (SingleObjectiveDecomposedProblem<?>) eval.getProblem();
		Range<Double> range = new Range<Double>();
		range.add(new Evaluator(decomposed.getProblem()).evaluate(s.getVariable()).getObjective());
		decomposed.setRange(range);
		
		
		if (s.getObjective().size() != 1) throw new RuntimeException("Only for single-objectives problems!");
		
		while (eval.hasNext()) {
			
			IVariable nextVar = mutation.mutate(s.getVariable().copy(), rand);
			
			range.add(new Evaluator(decomposed.getProblem()).evaluate(nextVar).getObjective());
			decomposed.setRange(range);
			
			Solution next = eval.evaluate(nextVar);
			s = eval.evaluate(s.getVariable());
			
			
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
