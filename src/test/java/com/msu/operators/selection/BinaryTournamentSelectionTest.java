package com.msu.operators.selection;

import org.junit.Before;
import org.junit.Test;

import com.msu.ExampleSolutionSet;
import com.msu.MockVariable;
import com.msu.moo.algorithms.nsgaII.CrowdingIndicator;
import com.msu.moo.algorithms.nsgaII.NonDominatedRankIndicator;
import com.msu.moo.algorithms.nsgaII.RankAndCrowdingComparator;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.MyRandom;

public class BinaryTournamentSelectionTest {
	

	private RankAndCrowdingComparator<MockVariable> cmp;
	
	private SolutionSet<MockVariable> l = ExampleSolutionSet.get();
	
	@Before
    public void setUp() {
        cmp = new RankAndCrowdingComparator<MockVariable>(new NonDominatedRankIndicator().calculate(l), new CrowdingIndicator().calculate(l));
    }
	
	@Test
	public void testSelectionNoException() {
		BinaryTournamentSelection<MockVariable> bts = new BinaryTournamentSelection<MockVariable>(l, cmp, new MyRandom());
		for (int i = 0; i < 100; i++) {
			bts.next();
		}
		
	}
	
	
	

}
