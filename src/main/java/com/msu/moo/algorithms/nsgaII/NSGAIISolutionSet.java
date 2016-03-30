package com.msu.moo.algorithms.nsgaII;

import java.util.Collection;

import com.msu.moo.model.GenericSolutionSet;

public class NSGAIISolutionSet<V> extends GenericSolutionSet<NSGAIISolution<V>, V>{

	public NSGAIISolutionSet() {
		super();
	}

	public NSGAIISolutionSet(Collection<NSGAIISolution<V>> c) {
		super(c);
	}

	public NSGAIISolutionSet(int initialCapacity) {
		super(initialCapacity);
	}

	
}
