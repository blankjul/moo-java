package com.msu.moo.algorithms.nsgaII;

import java.util.Comparator;

public class RankAndCrowdingComparator<V> implements Comparator<NSGAIISolution<V>> {


	@Override
	public int compare(NSGAIISolution<V> s1, NSGAIISolution<V> s2) {
		
		// check if rank for both exists
		if (s1.getRank() == null || s2.getRank() == null)
			throw new RuntimeException("Could not compare solutions because the rank details are not provided!");
		
		if (s1.getRank() < s2.getRank())
			return 1;
		else if (s1.getRank() > s2.getRank())
			return -1;
		else {
			
			// check if crowding for both exists
			if (s1.getCrowding() == null || s2.getCrowding() == null)
				throw new RuntimeException("Could not compare solutions because the crowding details are not provided!");
			
			if (s1.getCrowding() > s2.getCrowding())
				return 1;
			else if (s1.getCrowding() < s2.getCrowding())
				return -1;
			else
				return 0;
		}
	}

}
