package com.msu.moo.algorithms.nsgaII;

import java.util.Comparator;
import java.util.Map;

import com.msu.moo.model.solution.Solution;

public class RankAndCrowdingComparator<V> implements Comparator<Solution<V>> {

	// ! mapping for the current non dominated rank
	protected Map<Solution<V>, Integer> rank;

	// ! mapping of the crowding distance
	protected Map<Solution<V>, Double> crowding;

	public RankAndCrowdingComparator(Map<Solution<V>, Integer> rank, Map<Solution<V>, Double> crowding) {
		super();
		this.rank = rank;
		this.crowding = crowding;
	}

	@Override
	public int compare(Solution<V> o1, Solution<V> o2) {
		
		// check if rank for both exists
		if (!rank.containsKey(o1) || !rank.containsKey(o2))
			throw new RuntimeException("Could not compare solutions because the rank details are not provided!");
		
		if (rank.get(o1) < rank.get(o2))
			return 1;
		else if (rank.get(o1) > rank.get(o2))
			return -1;
		else {
			
			// check if crowding for both exists
			if (!crowding.containsKey(o1) || !crowding.containsKey(o2))
				throw new RuntimeException("Could not compare solutions because the crowding details are not provided!");
			
			if (crowding.get(o1) > crowding.get(o2))
				return 1;
			else if (crowding.get(o1) < crowding.get(o2))
				return -1;
			else
				return 0;
		}
	}

}
