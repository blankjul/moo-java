package com.msu.moo.algorithms.nsgaII;

import com.msu.interfaces.IEvaluator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Random;

/**
 * Different ideas that should be performed every generation could be injected
 * to NSGAII by adding an implementation of INSGAIIModifactor.
 *
 */
public interface INSGAIIModifactor {

	/**
	 * Modifies the given population.
	 */
	public void modify(IEvaluator eval, SolutionSet population, Random r);

}
