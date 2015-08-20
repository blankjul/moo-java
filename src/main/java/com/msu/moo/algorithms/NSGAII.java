package com.msu.moo.algorithms;

import com.msu.moo.model.Algorithm;
import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.SolutionSet;

public class NSGAII<V extends IVariable<?>, P extends IProblem<V,P>> extends Algorithm<V,P>{

	//! size of the whole Population
	protected int populationSize = 100;
	

	public NSGAII(IFactory<V> factory, long maxEvaluations) {
		super(factory, maxEvaluations);
	}

	@Override
	public NonDominatedSolutionSet run(P problem) {
		
		// initialize the population with populationSize
		SolutionSet P = new SolutionSet(populationSize);
		for (int i = 0; i < populationSize; i++) {
			P.add(problem.getEvaluator().run(factory.create()));
		}
		
		
		// Create the offspring population with crossover and mutation
		
		// Calculate the fronts
		
		// choose all except for last calculate Crowding
		
		// fill up
		
		
		
		return null;
	}



}
