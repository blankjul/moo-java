package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SingleObjectiveSolution;

public abstract class ASingleObjectiveAlgorithm<P extends IProblem> extends AbstractAlgorithm<P, NonDominatedSolutionSet>{

	protected abstract SingleObjectiveSolution run_(Evaluator<P> problem);
	
	@Override
	public NonDominatedSolutionSet run(Evaluator<P> evaluator) {
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		Solution mos = new Solution(run_(evaluator));
		set.add(mos);
		return set;
	}
	
}
