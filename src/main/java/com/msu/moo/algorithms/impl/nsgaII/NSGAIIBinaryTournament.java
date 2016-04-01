package com.msu.moo.algorithms.impl.nsgaII;

import java.util.Comparator;

import com.msu.moo.operators.selection.BinaryTournamentSelection;

public class NSGAIIBinaryTournament<V> extends BinaryTournamentSelection<NSGAIISolution<V>, V>{

	public NSGAIIBinaryTournament() {
		super(null);
		this.cmp = Comparator.comparingInt(NSGAIISolution::getRank);
		this.cmp = cmp.reversed();
		this.cmp = cmp.thenComparingDouble(NSGAIISolution::getCrowding);
	}

}
