package com.msu.moo.operators.selection;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.algorithms.nsgaII.CrowdingIndicator;
import com.msu.moo.algorithms.nsgaII.NSGAIISolution;
import com.msu.moo.algorithms.nsgaII.NSGAIISolutionSet;
import com.msu.moo.algorithms.nsgaII.NonDominatedRankIndicator;
import com.msu.moo.algorithms.nsgaII.RankAndCrowdingComparator;
import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.util.MyRandom;

public class BinaryTournamentSelectionTest {
	

	private RankAndCrowdingComparator<MockVariable> cmp = new RankAndCrowdingComparator<MockVariable>();
	
	private NSGAIISolutionSet<MockVariable> set = ExampleSolutionSet.get();
	
	
	@Before
    public void setUp() {
		NonDominatedRankIndicator.calculate(set);
		CrowdingIndicator.calculate(set);
    }
	
	@Test
	public void testSelectionNoException() {
		BinaryTournamentSelection<NSGAIISolution<MockVariable>, MockVariable> bts = new BinaryTournamentSelection<>(set, cmp, new MyRandom());
		for (int i = 0; i < 100; i++) {
			bts.next();
		}
		
	}
	
	
	

}
