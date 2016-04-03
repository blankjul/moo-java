package com.msu.moo.mock;

import java.util.Arrays;

import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;
import com.msu.moo.model.solution.SolutionSet;

public class ExampleSolutionSet {

	public static SolutionSet<MockSolution> get() {
		SolutionSet<MockSolution> l = new SolutionSet<>();
		l.add(MockSolution.create(Arrays.asList(0.1, 0.1)));
		l.add(MockSolution.create(Arrays.asList(0.15, 0.25)));
		l.add(MockSolution.create(Arrays.asList(0.2, 0.15)));
		l.add(MockSolution.create(Arrays.asList(0.3, 0.3)));
		return l;
	}
	
	public static SolutionSet<NSGAIISolution<MockVariable>> getAsNSGAIISolution() {
		SolutionSet<NSGAIISolution<MockVariable>> l = new SolutionSet<>();
		l.add(MockSolution.create(Arrays.asList(0.1, 0.1)));
		l.add(MockSolution.create(Arrays.asList(0.15, 0.25)));
		l.add(MockSolution.create(Arrays.asList(0.2, 0.15)));
		l.add(MockSolution.create(Arrays.asList(0.3, 0.3)));
		return l;
	}

}
