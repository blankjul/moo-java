package com.msu.moo.algorithms.impl.nsgaII;

import com.msu.moo.operators.selection.BinaryTournamentSelection;

public class NSGAIIBinaryTournament<S extends NSGAIISolution<?>> extends BinaryTournamentSelection<S>{

	public NSGAIIBinaryTournament() {
		super(new RankAndCrowdingComparator<>());
	}

}
