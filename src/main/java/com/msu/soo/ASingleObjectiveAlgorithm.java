package com.msu.soo;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.AbstractAlgorithm;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.util.MyRandom;

public abstract class ASingleObjectiveAlgorithm extends AbstractAlgorithm {

	public abstract Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand);
	
	@Override
	final public NonDominatedSolutionSet run_(IProblem problem, IEvaluator evaluator, MyRandom rand) {
/*		
		if (!(problem instanceof ASingleObjectiveProblem)) throw new RuntimeException("Single Objective algorithms "
				+ "could only work on SingleObjectiveProblems!");
		*/
		Solution s =  run__(problem, evaluator, rand);
		
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();
		set.add(s);
		return set;
	}


}
