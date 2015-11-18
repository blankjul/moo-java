package com.msu.moo.algorithms;

import com.msu.interfaces.IVariableFactory;
import com.msu.model.AbstractAlgorithm;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.AbstractMutation;

/**
 * This class should be used as a template for all evolutionary algorithms that
 * are used. 
 */
public abstract class EvolutionaryAlgorithms extends AbstractAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! operator for crossover
	protected AbstractCrossover<?> crossover;

	// ! operator for mutation
	protected AbstractMutation<?> mutation;

	// ! factory for creating new instances
	protected IVariableFactory factory;

	// ! current population
	protected SolutionSet population = null;

	
}
