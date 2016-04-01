package com.msu.moo.fonseca;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.mock.MockNonDominatedSet;
import com.msu.moo.model.solution.NonDominatedSet;

public class EmpiricalAttainmentFunctionTest {

	
	@Test
	public void testHVNoRef() {
		Collection<Collection<? extends ISolution<?>>>  fronts = new ArrayList<>();
		fronts.add(MockNonDominatedSet.create(new Double[][] {{0.0,0.5}, {0.5,0.5}, {0.0,1.0}}).getSolutions());
		fronts.add(MockNonDominatedSet.create(new Double[][] {{0.0,0.2}, {0.5,0.5}, {0.0,1.0}}).getSolutions());
		fronts.add(MockNonDominatedSet.create(new Double[][] {{1.0,0.7}, {0.5,0.5}, {0.0,1.0}}).getSolutions());
		NonDominatedSet<ISolution<Object>, Object> d = EmpiricalAttainmentFunction.calculate(fronts);
		assertTrue(!d.getSolutions().isEmpty());
	}

	
	
}
