package com.msu.moo.mock;

import java.util.Arrays;

import com.msu.moo.algorithms.nsgaII.NSGAIISolution;
import com.msu.moo.algorithms.nsgaII.NSGAIISolutionSet;

public class ExampleSolutionSet {

	public static NSGAIISolutionSet<MockVariable> get() {
		NSGAIISolutionSet<MockVariable> l = new NSGAIISolutionSet<>();
		l.add(new NSGAIISolution<>(new MockVariable() , Arrays.asList(0.1, 0.1)));
		l.add(new NSGAIISolution<>(new MockVariable() , Arrays.asList(0.15, 0.25)));
		l.add(new NSGAIISolution<>(new MockVariable() , Arrays.asList(0.2, 0.15)));
		l.add(new NSGAIISolution<>(new MockVariable() , Arrays.asList(0.3, 0.3)));
		return l;
	}

}
