package com.msu.moo.algorithms.nsgaII;


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.msu.moo.mock.ExampleSolutionSet;
import com.msu.moo.mock.MockSolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.sorting.SortingBestOrder;
import com.msu.moo.sorting.indicator.NonDominatedRankIndicator;

public class NonDominatedRankIndicatorTest {

	
	@Test
	@Ignore
	public void testCalcValuesOfExample() {
		SolutionSet<MockSolution> l = ExampleSolutionSet.get();
		NonDominatedRankIndicator.calculate(l);
		assertEquals(new Integer(0), l.get(0).getRank());
		assertEquals(new Integer(1), l.get(1).getRank());
		assertEquals(new Integer(1), l.get(2).getRank());
		assertEquals(new Integer(2), l.get(3).getRank());
	}
	
	@Test
	public void testCalcValuesOfExampleWithConstraints() {
		SolutionSet<MockSolution> l = new SolutionSet<>();
		l.add(MockSolution.create(1, Arrays.asList(5.0), Arrays.asList(2.0)));
		l.add(MockSolution.create(2, Arrays.asList(100.0), Arrays.asList(1.0)));
		l.add(MockSolution.create(3, Arrays.asList(20.0), Arrays.asList(1.0)));
		l.add(MockSolution.create(4, Arrays.asList(100.0), Arrays.asList(0.0)));
		
		List<NonDominatedSet<MockSolution>> sets = SortingBestOrder.sort(l);
		NonDominatedRankIndicator.assign(sets);
		
		assertEquals(new Integer(2), l.get(0).getRank());
		assertEquals(new Integer(1), l.get(1).getRank());
		assertEquals(new Integer(1), l.get(2).getRank());
		assertEquals(new Integer(0), l.get(3).getRank());
	}

	
}
