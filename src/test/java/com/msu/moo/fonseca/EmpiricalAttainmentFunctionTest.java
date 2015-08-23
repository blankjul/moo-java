package com.msu.moo.fonseca;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.msu.moo.mocks.MockSolution;
import com.msu.moo.model.solution.SolutionSet;

public class EmpiricalAttainmentFunctionTest {

	
	private EmpiricalAttainmentFunction eaf = new EmpiricalAttainmentFunction();
	
	@Test
	public void testHVNoRef() {
		List<SolutionSet> fronts = new ArrayList<>();
		fronts.add(MockSolution.create(new Double[][] {{0.0,0.5}, {0.5,0.5}, {0.0,1.0}}));
		fronts.add(MockSolution.create(new Double[][] {{0.0,0.2}, {0.5,0.5}, {0.0,1.0}}));
		fronts.add(MockSolution.create(new Double[][] {{1.0,0.7}, {0.5,0.5}, {0.0,1.0}}));
		SolutionSet set = eaf.calculate(fronts);
		System.out.println(set);
	}

	
	
}
