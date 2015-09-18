package com.msu.moo.util.comparator;

import java.util.Comparator;
import java.util.Map;

import com.msu.moo.model.solution.MultiObjectiveSolution;

public class RankAndCrowdingComparator implements Comparator<MultiObjectiveSolution> {

	// ! mapping for the current non dominated rank
	protected Map<MultiObjectiveSolution, Integer> rank;

	// ! mapping of the crowding distance
	protected Map<MultiObjectiveSolution, Double> crowding;

	public RankAndCrowdingComparator(Map<MultiObjectiveSolution, Integer> rank, Map<MultiObjectiveSolution, Double> crowding) {
		super();
		this.rank = rank;
		this.crowding = crowding;
	}

	@Override
	public int compare(MultiObjectiveSolution o1, MultiObjectiveSolution o2) {
		
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
