package com.msu.moo.algorithms;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IMutation;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.model.AMultiObjectiveAlgorithm;
import com.msu.moo.model.solution.SolutionSet;

/**
 * This class should be used as a template for all evolutionary algorithms that
 * are used. 
 */
public abstract class AMultiObjectiveEvolutionaryAlgorithm<V extends IVariable, P extends IProblem<V>>  extends AMultiObjectiveAlgorithm<V,P> {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! operator for crossover
	protected ICrossover<V> crossover;

	// ! operator for mutation
	protected IMutation<V> mutation;

	// ! factory for creating new instances
	protected IFactory<V> factory;
	
	// ! current population
	protected SolutionSet<V> population = null;

	
	
	
	
}
