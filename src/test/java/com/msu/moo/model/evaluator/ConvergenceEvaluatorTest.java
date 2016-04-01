package com.msu.moo.model.evaluator;

import java.util.Arrays;

import com.msu.moo.mock.MockSolution;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.SolutionSet;

import junit.framework.TestCase;

public class ConvergenceEvaluatorTest extends TestCase{


	
	public void testSingleObjectiveNotify() {
		ConvergenceEvaluator eval = new ConvergenceEvaluator(100, 10);
		for (int i = 0; i < 100; i++) {
			eval.notify(MockSolution.create(1, Arrays.asList(5d,2d)));
			assertTrue(eval.hasNext());
		}
		eval.notify(MockSolution.create(1, Arrays.asList(5d,2d)));
		assertFalse(eval.hasNext());
		
	}
	
	
	public void testMultiObjectiveNotify() {
		ConvergenceEvaluator eval = new ConvergenceEvaluator(100);
		

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
		
	}
	
	
	
}
