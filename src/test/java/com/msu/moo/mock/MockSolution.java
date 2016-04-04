package com.msu.moo.mock;

import java.util.Arrays;
import java.util.List;

import com.msu.moo.algorithms.impl.nsgaII.NSGAIISolution;

public class MockSolution extends NSGAIISolution<MockVariable> {

	public MockSolution(List<Double> objectives) {
		super(MockVariable.create(), objectives);
	}
	
	public MockSolution(List<Double> objectives, List<Double> constraintViolations) {
		super(MockVariable.create(), objectives, constraintViolations);
	}
	
	public MockSolution(MockVariable variable, List<Double> objectives) {
		super(variable, objectives);
	}

	public MockSolution(MockVariable variable, List<Double> objectives, List<Double> constraintViolations) {
		super(variable, objectives, constraintViolations);
	}
	
	
	public static MockSolution create(List<Double> objectives) {
		return new MockSolution(objectives);
	}
	
	public static MockSolution create(double obj1, double obj2) {
		return MockSolution.create(Arrays.asList(obj1, obj2));
	}
	
	public static MockSolution create(List<Double> objectives, List<Double> constraintViolations) {
		return new MockSolution(objectives, constraintViolations);
	}
	
	public static MockSolution create(Integer i, List<Double> objectives) {
		return new MockSolution(new MockVariable(i), objectives);
	}
	
	public static MockSolution create(Integer i, List<Double> objectives, List<Double> constraintViolations) {
		return new MockSolution(new MockVariable(i), objectives, constraintViolations);
	}
	

}
