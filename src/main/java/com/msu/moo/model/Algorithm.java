package com.msu.moo.model;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;


public abstract class Algorithm<V extends IVariable<?>, P extends IProblem<V,P>> implements IAlgorithm<V,P> {
	

	//! factory for producing new variables
	protected IFactory<V> factory = null;
	
	//! maximal number of evaluations
	protected long maxEvaluations;

	public Algorithm(IFactory<V> factory, long maxEvaluations) {
		super();
		this.factory = factory;
		this.maxEvaluations = maxEvaluations;
	}
	
	
	
	
}
