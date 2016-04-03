package com.msu.moo.model.solution;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.mock.MockSolution;

public class SingleObjectiveComparatorTest {

	@Test
	public void testSortAccordingSingle() {
		MockSolution s1 = MockSolution.create(Arrays.asList(1d,1d), Arrays.asList(0d, 0d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,0d), Arrays.asList(0d, 0d));
		
		SolutionSet<MockSolution> set = new SolutionSet<>();
		set.add(s1);
		set.add(s2);
		
		set.sort(new SolutionObjectiveComparator<>());
		
		assertEquals(s2, set.get(0));
		assertEquals(s1, set.get(1));
		
	}
	
	@Test
	public void testSortButConstraintsViolated() {
		MockSolution s1 = MockSolution.create(Arrays.asList(1d,1d), Arrays.asList(0d, 0d));
		MockSolution s2 = MockSolution.create(Arrays.asList(1d,0d), Arrays.asList(1d, 0d));
		
		SolutionSet<MockSolution> set = new SolutionSet<>();
		set.add(s1);
		set.add(s2);
		
		set.sort(new SolutionObjectiveComparator<>());
		
		assertEquals(s1, set.get(0));
		assertEquals(s2, set.get(1));
		
	}
	
	
	
}
