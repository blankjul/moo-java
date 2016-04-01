package com.msu.moo.algorithms.impl.nsgaII;

import java.util.List;

import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;

public class NonDominatedRankIndicator {
	

	public static <S extends NSGAIISolution<V>, V> void calculate(SolutionSet<S,V> solutions) {
		
		// sort the population
		List<NonDominatedSet<S,V>> sets = NaiveNonDominatedSorting.sort(solutions);

		int ranking = 0;
		for(NonDominatedSet<S,V> set : sets) {
			for(S s : set) {
				s.setRank(ranking);
			}
			ranking++;
		}
	}
	
	

}
