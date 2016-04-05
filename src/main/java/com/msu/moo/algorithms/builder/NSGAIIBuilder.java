package com.msu.moo.algorithms.builder;

import com.msu.moo.algorithms.impl.nsgaII.NSGAII;
import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.util.Builder;

public class NSGAIIBuilder<V extends IEvolutionaryVariable<?>, P extends IProblem<V>> extends Builder<NSGAII<V,P>> {

	public NSGAIIBuilder() {
		super(NSGAII.class);
		
		propertiesToSet.add("populationSize");
		propertiesToSet.add("probMutation");
		propertiesToSet.add("crossover");
		propertiesToSet.add("mutation");
		propertiesToSet.add("factory");
		
	}

	
}
