package com.msu.moo.algorithms.impl.nsgaII;

import java.util.Comparator;

public class RankAndCrowdingComparator  {

	public static <V> Comparator<NSGAIISolution<V>> build() {
		Comparator<NSGAIISolution<V>> cmp = Comparator.comparingInt(NSGAIISolution::getRank);
		cmp = cmp.reversed();
		cmp = cmp.thenComparingDouble(NSGAIISolution::getCrowding);
		return cmp;
	}
	


}
