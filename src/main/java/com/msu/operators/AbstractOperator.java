package com.msu.operators;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.util.MyRandom;

public class AbstractOperator {

	
	// random generator
	protected MyRandom rand = null;
	
	// specific problem instance
	protected IProblem problem = null;
	
	// evaluator
	protected IEvaluator evaluator = null;


	
}
