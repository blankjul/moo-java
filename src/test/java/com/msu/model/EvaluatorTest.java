package com.msu.model;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.problems.Kursawe;
import com.msu.util.exceptions.EvaluationException;

public class EvaluatorTest {

	@Test
	public void testCountingOfDescendant() {
		Evaluator<DoubleListVariable> parent = new Evaluator<>(100);
		parent.evaluate(new Kursawe(), new DoubleListVariable(Arrays.asList(1.0,1.0,1.0)));
		parent.createChildEvaluator(100).evaluate(new Kursawe(), new DoubleListVariable(Arrays.asList(1.0,1.0,1.0)));
		assertEquals(2, parent.getEvaluations());
		
	}
	
	@Test (expected=EvaluationException.class) 
	public void testEvaluateMoreThanMaxEvaluationsException() throws RuntimeException {
		Evaluator<DoubleListVariable> parent = new Evaluator<DoubleListVariable>(100);
		for (int i = 0; i < 121; i++) {
			parent.evaluate(new Kursawe(), new DoubleListVariable(Arrays.asList(1.0,1.0,1.0)));
		}
	}
	
	
	
}
