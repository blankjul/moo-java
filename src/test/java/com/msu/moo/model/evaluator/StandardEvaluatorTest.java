package com.msu.moo.model.evaluator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.problems.Kursawe;
import com.msu.moo.util.exceptions.EvaluationException;

public class StandardEvaluatorTest {

	@Test
	public void testCountingOfDescendant() {
		StandardEvaluator parent = new StandardEvaluator(100);
		parent.evaluate(new Kursawe(), new DoubleListVariable(Arrays.asList(1.0,1.0,1.0)));
		
		IEvaluator child = new StandardEvaluator(100);
		child.setFather(parent);
		
		child.evaluate(new Kursawe(), new DoubleListVariable(Arrays.asList(1.0,1.0,1.0)));
		assertEquals(2, parent.getEvaluations());
		
	}
	
	@Test (expected=EvaluationException.class) 
	public void testEvaluateMoreThanMaxEvaluationsException() throws RuntimeException {
		StandardEvaluator parent = new StandardEvaluator(100);
		for (int i = 0; i < 121; i++) {
			parent.evaluate(new Kursawe(), new DoubleListVariable(Arrays.asList(1.0,1.0,1.0)));
		}
	}
	
	
	
}
