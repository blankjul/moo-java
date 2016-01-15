package com.msu.moo.algorithms.nsgaII;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.model.AIndicator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.model.solution.SolutionSet;

public class NonDominatedRankIndicator extends AIndicator<Integer> {

	// ! NonDominatedSorting that is used for calculating the rank
	protected NonDominatedSorting sort = new NaiveNonDominatedSorting(new SolutionDominatorWithConstraints());
	
	
	public NonDominatedRankIndicator() {
		super();
	}

	public NonDominatedRankIndicator(NonDominatedSorting sort) {
		super();
		this.sort = sort;
	}

	@Override
	public <T> Map<Solution<T>, Integer> calculate(SolutionSet<T> solutions) {
		
		// sort the population
		List<NonDominatedSolutionSet<T>> sets = sort.run(solutions);

		Map<Solution<T>, Integer> map = new HashMap<Solution<T>, Integer>();
		calculate(map, sets);
		return map;
	}
	
	
	public <T> void calculate(Map<Solution<T>, Integer> map, List<NonDominatedSolutionSet<T>> sets) {
		for (int i = 0; i < sets.size(); i++) {
			for (Solution<T> s : sets.get(i).getSolutions())
				map.put(s, i);
		}
	}

	

	

}
