package com.msu.moo.algorithms;

import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.interfaces.VariableFactory;
import com.msu.moo.operators.AbstractCrossover;
import com.msu.moo.operators.AbstractMutation;

public class NSGAIIBuilder<V extends IVariable, P extends IProblem<V>> {

	protected int populationSize = 100;

	protected double probMutation = 0.2;

	protected AbstractCrossover<?> crossover;

	protected AbstractMutation<?> mutation;

	protected VariableFactory<V, P> factory;

	protected String name;

	public NSGAIIBuilder<V, P> setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
		return this;
	}

	public NSGAIIBuilder<V, P> setProbMutation(double probMutation) {
		this.probMutation = probMutation;
		return this;
	}

	public NSGAIIBuilder<V, P> setCrossover(AbstractCrossover<?> crossover) {
		this.crossover = crossover;
		return this;
	}

	public NSGAIIBuilder<V, P> setMutation(AbstractMutation<?> mutation) {
		this.mutation = mutation;
		return this;
	}

	public NSGAIIBuilder<V, P> setFactory(VariableFactory<V, P> factory) {
		this.factory = factory;
		return this;
	}

	public NSGAIIBuilder<V, P> setName(String name) {
		this.name = name;
		return this;
	}

	public NSGAII<V, P> create() {
		NSGAII<V, P> nsgaII = new NSGAII<>(factory, crossover, mutation);
		if (name != null) nsgaII.setName(name);
		nsgaII.populationSize = populationSize;
		return nsgaII;
	}

}