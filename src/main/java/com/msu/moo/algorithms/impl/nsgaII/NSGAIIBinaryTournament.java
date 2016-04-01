package com.msu.moo.algorithms.impl.nsgaII;

import com.msu.moo.operators.selection.BinaryTournamentSelection;

public class NSGAIIBinaryTournament<V> extends BinaryTournamentSelection<NSGAIISolution<V>, V>{

	public NSGAIIBinaryTournament() {
		super(RankAndCrowdingComparator.build());
	}

}
