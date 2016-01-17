package com.msu.moo.fonseca;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.msu.moo.Configuration;
import com.msu.moo.mock.MockNonDominatedSet;
import com.msu.moo.mock.MockVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class EmpiricalAttainmentFunctionTest {

	
	private EmpiricalAttainmentFunction eaf = new EmpiricalAttainmentFunction(Configuration.PATH_TO_EAF);
	
	@Test
	public void testHVNoRef() {
		List<NonDominatedSolutionSet<MockVariable>> fronts = new ArrayList<>();
		fronts.add(MockNonDominatedSet.create(new Double[][] {{0.0,0.5}, {0.5,0.5}, {0.0,1.0}}));
		fronts.add(MockNonDominatedSet.create(new Double[][] {{0.0,0.2}, {0.5,0.5}, {0.0,1.0}}));
		fronts.add(MockNonDominatedSet.create(new Double[][] {{1.0,0.7}, {0.5,0.5}, {0.0,1.0}}));
		NonDominatedSolutionSet<MockVariable> d = eaf.calculate(fronts);
		assertTrue(!d.getSolutions().isEmpty());
	}

	
	
}
