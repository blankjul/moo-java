package com.msu.moo.operators.selection;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.sorting.indicator.CrowdingDistance;
import com.msu.moo.sorting.indicator.NonDominatedRankIndicator;

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
