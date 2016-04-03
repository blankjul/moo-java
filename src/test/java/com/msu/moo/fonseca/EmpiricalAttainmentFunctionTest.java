package com.msu.moo.fonseca;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.mock.Factory;
import com.msu.moo.model.solution.NonDominatedSet;

public class EmpiricalAttainmentFunctionTest {

	
	@Test
	public void testHVNoRef() {
		Collection<Collection<? extends ISolution<?>>>  fronts = new ArrayList<>();
		fronts.add(Factory.create(new Double[][] {{0.0,0.5}, {0.5,0.5}, {0.0,1.0}}));
		fronts.add(Factory.create(new Double[][] {{0.0,0.2}, {0.5,0.5}, {0.0,1.0}}));
		fronts.add(Factory.create(new Double[][] {{1.0,0.7}, {0.5,0.5}, {0.0,1.0}}));
		NonDominatedSet<ISolution<Object>> d = EmpiricalAttainmentFunction.calculate(fronts);
		assertTrue(!d.getSolutions().isEmpty());
	}

	
	
}
