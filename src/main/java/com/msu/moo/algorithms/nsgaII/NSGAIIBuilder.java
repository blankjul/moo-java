package com.msu.moo.algorithms.nsgaII;

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

	protected INSGAIIModifactor funcModify = null;

	
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

	public NSGAIIBuilder setFuncModify(INSGAIIModifactor funcModify) {
		this.funcModify = funcModify;
		return this;
	}

	public NSGAII create() {
		NSGAII nsgaII = new NSGAII();
		if (name != null)
			nsgaII.setName(name);
		nsgaII.setPopulationSize(populationSize);
		nsgaII.setProbMutation(probMutation);
		nsgaII.setFactory(factory);
		nsgaII.setCrossover(crossover);
		nsgaII.setMutation(mutation);
		nsgaII.funcModify = funcModify;
		return nsgaII;
	}

}