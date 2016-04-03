package com.msu.moo.algorithms.impl.nsgaII;

import java.util.Comparator;

public class RankAndCrowdingComparator<S extends NSGAIISolution<?>> implements Comparator<S>{

	@Override
	public int compare(S o1, S o2) {
		int result = Integer.compare(o2.getRank(), o1.getRank());
		if (result == 0) {
			result = Double.compare(o1.getCrowding(), o2.getCrowding());
		}
		return result;
	}
}
