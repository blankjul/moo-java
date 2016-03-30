package com.msu.moo.algorithms.nsgaII;

import java.util.List;

import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;

public class NonDominatedRankIndicator {
	

	public static <V> void calculate(NSGAIISolutionSet<V> solutions) {
		
		// sort the population
		List<NonDominatedSolutionSet<V>> sets = NaiveNonDominatedSorting.sort(solutions);

		int ranking = 0;
		for(NonDominatedSolutionSet<V> set : sets) {
			for(Solution<V> s : set) {
				((NSGAIISolution<V>)s).setRank(ranking);
			}
			ranking++;
		}
	}
	
	

}
