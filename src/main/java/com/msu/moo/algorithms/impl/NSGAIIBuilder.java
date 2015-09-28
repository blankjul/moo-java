package com.msu.moo.algorithms.impl;

import com.msu.moo.interfaces.IVariableFactory;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.operators.AbstractMutation;

public class NSGAIIBuilder {

	protected int populationSize = 100;

	protected double probMutation = 0.3;

	protected AbstractCrossover<?> crossover;

	protected AbstractMutation<?> mutation;

	protected IVariableFactory factory;

	protected String name;

	public NSGAIIBuilder setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
		return this;
	}

	public NSGAIIBuilder setProbMutation(double probMutation) {
		this.probMutation = probMutation;
		return this;
	}

	public NSGAIIBuilder setCrossover(AbstractCrossover<?> crossover) {
		this.crossover = crossover;
		return this;
	}

	public NSGAIIBuilder setMutation(AbstractMutation<?> mutation) {
		this.mutation = mutation;
		return this;
	}

	public NSGAIIBuilder setFactory(IVariableFactory factory) {
		this.factory = factory;
		return this;
	}

	public NSGAIIBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public NSGAII create() {
		NSGAII nsgaII = new NSGAII();
		if (name != null)
			nsgaII.setName(name);
		nsgaII.populationSize = populationSize;
		nsgaII.probMutation = probMutation;
		nsgaII.factory = factory;
		nsgaII.crossover = crossover;
		nsgaII.mutation = mutation;
		return nsgaII;
	}

}