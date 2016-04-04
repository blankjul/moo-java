package com.msu.moo.sorting.indicator;

import java.util.List;

import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.sorting.SortingNaive;

public class NonDominatedRankIndicator {
	

	public static <S extends NSGAIISolution<?>> void calculate(SolutionSet<S> solutions) {
		List<NonDominatedSet<S>> sets = SortingNaive.sort(solutions);
		assign(sets);
	}
	
	public static <S extends NSGAIISolution<?>> void assign(List<NonDominatedSet<S>> sets) {
		int ranking = 0;
		for(NonDominatedSet<S> set : sets) {
			for(S s : set) {
				s.setRank(ranking);
			}
			ranking++;
		}
	}
	
	

}
