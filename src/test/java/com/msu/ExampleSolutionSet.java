package com.msu;

import java.util.ArrayList;
import java.util.Arrays;

import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.SolutionSet;

public class ExampleSolutionSet {

	public static SolutionSet get() {
		SolutionSet l = new SolutionSet();
		l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.1, 0.1))));
		l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.15, 0.25))));
		l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.2, 0.15))));
		l.add(new MockSolution(new ArrayList<Double>(Arrays.asList(0.3, 0.3))));
		return l;
	}

}
