package com.msu.moo.algorithms;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IMutation;
import com.msu.interfaces.IProblem;
import com.msu.model.AbstractAlgorithm;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.OperatorFactory;
import com.msu.util.MyRandom;

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
	protected OperatorFactory<ICrossover> fCrossover;

	// ! operator for mutation
	protected OperatorFactory<IMutation> fMutation;

	// ! factory for creating new instances
	protected OperatorFactory<IFactory> fFactory;
	
	protected ICrossover crossover;
	protected IMutation mutation;
	protected IFactory factory;
	

	// ! current population
	protected SolutionSet population = null;

	
	public void initialize(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		this.crossover = fCrossover.create(problem, rand, evaluator);
		this.mutation = fMutation.create(problem, rand, evaluator);
		this.factory = fFactory.create(problem, rand, evaluator);
	}
		
	
	

	
	
}
