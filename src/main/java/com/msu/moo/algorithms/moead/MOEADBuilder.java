package com.msu.moo.algorithms.moead;

import com.msu.interfaces.IVariableFactory;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.AbstractMutation;

public class MOEADBuilder {

	protected int populationSize = 100;

	protected double probMutation = 0.3;

	protected AbstractCrossover<?> crossover;

	protected AbstractMutation<?> mutation;

	protected IVariableFactory factory;

	protected String name;
	
	protected int T = 10;

	protected double delta = 1.0;
	
	protected int n_r = T;

	
	public MOEADBuilder setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
		return this;
	}

	public MOEADBuilder setProbMutation(double probMutation) {
		this.probMutation = probMutation;
		return this;
	}

	public MOEADBuilder setCrossover(AbstractCrossover<?> crossover) {
		this.crossover = crossover;
		return this;
	}

	public MOEADBuilder setMutation(AbstractMutation<?> mutation) {
		this.mutation = mutation;
		return this;
	}
	
	public MOEADBuilder setT(int T) {
		this.T = T;
		this.n_r = T;
		return this;
	}
	
	public MOEADBuilder setN_r(int value) {
		this.n_r = value;
		return this;
	}
	
	public MOEADBuilder setDelta(double delta) {
		this.delta = delta;
		return this;
	}


	public MOEADBuilder setFactory(IVariableFactory factory) {
		this.factory = factory;
		return this;
	}

	public MOEADBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public MOEAD create() {
		MOEAD moead = new MOEAD();
		if (name != null)
			moead.setName(name);
		moead.setPopulationSize(populationSize);
		moead.setProbMutation(probMutation);
		moead.setFactory(factory);
		moead.setCrossover(crossover);
		moead.setMutation(mutation);
		moead.T = T;
		moead.delta = delta;
		moead.n_r = n_r;
		return moead;
	}

}