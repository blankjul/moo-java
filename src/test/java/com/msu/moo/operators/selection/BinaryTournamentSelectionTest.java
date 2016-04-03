package com.msu.moo.operators.selection;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.algorithms.impl.nsgaII.CrowdingDistance;
import com.msu.moo.algorithms.impl.nsgaII.NonDominatedRankIndicator;
import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.model.solution.SolutionSet;

public class BinaryTournamentSelectionTest {
	
	private SolutionSet<MockSolution> set = ExampleSolutionSet.get();
	
	@Before
    public void setUp() {
		NonDominatedRankIndicator.calculate(set);
		CrowdingDistance.calculate(set);
    }
	
	@Test
	public void testEmpty() {
		
	}
	

}
