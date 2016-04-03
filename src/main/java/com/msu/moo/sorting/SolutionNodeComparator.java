package com.msu.moo.sorting;

import java.util.Comparator;
import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.SolutionSet;

public class SolutionNodeComparator<S extends ISolution<?>> implements Comparator<SolutionNode>{

	// the population which is accessed by index for the sorting
	protected SolutionSet<S> population;
	
	// objective according the Node List should be sorted 
	protected int objective;
	
	
	public SolutionNodeComparator(SolutionSet<S> population, int objective) {
		super();
		this.population = population;
		this.objective = objective;
	}


	@Override
	public int compare(SolutionNode o1, SolutionNode o2) {
		
		final int m = population.get(0).numOfObjectives();
		
		for (int i = objective; i < objective + m; i++) {
			final int idx = i % m;
			List<Double> a = population.get(o1.index).getObjectives();
			List<Double> b = population.get(o2.index).getObjectives();
			int cmp = Double.compare(a.get(idx), b.get(idx));
			
			// difference found
			if (cmp != 0) return cmp;
		}
		
		// solutions are equal
		return 0;
		
	}

}
