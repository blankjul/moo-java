package com.msu.moo.indicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.sorting.NaiveNonDominatedSorting;
import com.msu.moo.util.sorting.NonDominatedSorting;

public class NonDominatedRankIndicator implements Indicator<Integer>{

	//! NonDominatedSorting that is used for caclulating the rank
	NonDominatedSorting sort = new NaiveNonDominatedSorting();

	public NonDominatedRankIndicator() {
		super();
	}
	

	public NonDominatedRankIndicator(NonDominatedSorting sort) {
		super();
		this.sort = sort;
	}

	@Override
	public Map<Solution, Integer> calculate(SolutionSet solutions) {
		
		// sort the population
		List<NonDominatedSolutionSet> fronts = sort.run(solutions);
		
		// assign integer according to result list
		Map<Solution, Integer> map = new HashMap<>();
		for (int i = 0; i < fronts.size(); i++) {
			for(Solution s : fronts.get(i).getSolutions()) map.put(s, i);
		}

		return map;
	}

}
