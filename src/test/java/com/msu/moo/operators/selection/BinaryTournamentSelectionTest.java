package com.msu.moo.operators.selection;

import org.junit.Before;
import org.junit.Test;

import com.msu.moo.ExampleSolutionSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Random;
import com.msu.moo.util.comparator.RankAndCrowdingComparator;
import com.msu.moo.util.indicator.CrowdingIndicator;
import com.msu.moo.util.indicator.NonDominatedRankIndicator;

public class BinaryTournamentSelectionTest {
	

	private RankAndCrowdingComparator cmp;
	
	private SolutionSet l = ExampleSolutionSet.get();
	
	@Before
    public void setUp() {
        cmp = new RankAndCrowdingComparator(new NonDominatedRankIndicator().calculate(l), new CrowdingIndicator().calculate(l));
    }
	
	@Test
	public void testSelectionNoException() {
		BinaryTournamentSelection bts = new BinaryTournamentSelection(l, cmp, new Random());
		for (int i = 0; i < 100; i++) {
			bts.next();
		}
		
	}
	
	
	

}
