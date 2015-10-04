package com.msu.moo.util.indicator;

import java.util.List;
import java.util.Map;

import com.msu.moo.model.AIndicator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionDominatorWithConstraints;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.sorting.NaiveNonDominatedSorting;
import com.msu.moo.util.sorting.NonDominatedSorting;

public class NonDominatedRankIndicator extends AIndicator<Integer> {

	// ! NonDominatedSorting that is used for calculating the rank
	protected NonDominatedSorting sort = new NaiveNonDominatedSorting(new SolutionDominatorWithConstraints());
	
	//! when you have calculated the fronts you have access to the list of fronts.
	protected List<NonDominatedSolutionSet> sets = null;

	
	public NonDominatedRankIndicator() {
		super();
	}

	public NonDominatedRankIndicator(NonDominatedSorting sort) {
		super();
		this.sort = sort;
	}

	@Override
	public void calculate(Map<Solution, Integer> map, SolutionSet solutions) {
		// sort the population
		sets = sort.run(solutions);

		// assign integer according to result list
		for (int i = 0; i < sets.size(); i++) {
			for (Solution s : sets.get(i).getSolutions())
				map.put(s, i);
		}
	}

	/**
	 * @return all the fronts that were used to determine the rank of each solution
	 */
	public List<NonDominatedSolutionSet> getNonDominatedSets() {
		return sets;
	}
	
	

}
