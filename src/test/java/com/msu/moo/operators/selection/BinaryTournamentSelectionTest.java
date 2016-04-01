package com.msu.moo.operators.selection;

import org.junit.Before;

import com.msu.moo.algorithms.impl.nsgaII.CrowdingDistance;
import com.msu.moo.algorithms.impl.nsgaII.NonDominatedRankIndicator;
import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockSolutionSet;

public class BinaryTournamentSelectionTest {
	
	
	private MockSolutionSet set = ExampleSolutionSet.get();
	
	@Before
    public void setUp() {
		NonDominatedRankIndicator.calculate(set);
		CrowdingDistance.calculate(set);
    }
	

	
	

}
