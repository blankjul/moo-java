package com.msu.moo.model.evaluator;

import org.junit.Ignore;

import junit.framework.TestCase;

public class ConvergenceEvaluatorTest extends TestCase{


	@Ignore
	public void testSingleObjectiveNotify() {
		
		/*
		SingleObectiveConvergenceEvaluator eval = new SingleObectiveConvergenceEvaluator(100, 10);
		for (int i = 0; i < 100; i++) {
			eval.notify(MockSolution.create(1, Arrays.asList(5d,2d)));
			assertTrue(eval.hasNext());
		}
		eval.notify(MockSolution.create(1, Arrays.asList(5d,2d)));
		assertFalse(eval.hasNext());
		*/
		
	}
	
	@Ignore
	public void testMultiObjectiveNotify() {
		/*
		SingleObectiveConvergenceEvaluator eval = new SingleObectiveConvergenceEvaluator(100);
		

		for (int i = 0; i < 100; i++) {
			SolutionSet<MockSolution, MockVariable> set = new SolutionSet<>();
			set.add(MockSolution.create(1, Arrays.asList(5d,2d)));
			set.add(MockSolution.create(2, Arrays.asList(4d,3d)));
			eval.notify(set);
			assertTrue(eval.hasNext());
		}
		
		SolutionSet<MockSolution, MockVariable> set = new SolutionSet<>();
		set.add(MockSolution.create(1, Arrays.asList(5d,2d)));
		set.add(MockSolution.create(2, Arrays.asList(4d,3d)));
		
		eval.notify(set);
		assertFalse(eval.hasNext());
		*/
		
	}

	
	
	
}
