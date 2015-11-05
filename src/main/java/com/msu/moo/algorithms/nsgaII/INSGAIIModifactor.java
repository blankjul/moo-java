package com.msu.moo.algorithms.nsgaII;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Random;

public interface INSGAIIModifactor {
	
	public void modify(IEvaluator eval, SolutionSet population, Random r);

}
