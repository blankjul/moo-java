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

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public double getProbMutation() {
		return probMutation;
	}

	public void setProbMutation(double probMutation) {
		this.probMutation = probMutation;
	}

	public AbstractCrossover<?> getCrossover() {
		return crossover;
	}

	public void setCrossover(AbstractCrossover<?> crossover) {
		this.crossover = crossover;
	}

	public AbstractMutation<?> getMutation() {
		return mutation;
	}

	public void setMutation(AbstractMutation<?> mutation) {
		this.mutation = mutation;
	}

	public IVariableFactory getFactory() {
		return factory;
	}

	public void setFactory(IVariableFactory factory) {
		this.factory = factory;
	}

	public SolutionSet getPopulation() {
		return population;
	}

	public void setPopulation(SolutionSet population) {
		this.population = population;
	}

}
