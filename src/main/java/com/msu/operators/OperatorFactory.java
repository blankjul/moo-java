package com.msu.operators;

import com.msu.builder.Builder;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.util.MyRandom;

/**
 * This class allows to create a factory for operators. The advantage is that
 * the algorithm only have the parameter of the class without specifying the
 * problem, random or evaluator.
 * 
 * These parameter should also not be relevant for the pure operation such as
 * crossover and mutation.
 *
 * @param <T>
 *            type of operator
 */
public class OperatorFactory<T> {

	protected Builder<T> b;
	

	public OperatorFactory(Class<T> clazz) {
		this.b = new Builder<>(clazz);
	}
	
	public OperatorFactory(T obj) {
		this.b = new Builder<>(obj);
	}

	public T create(IProblem problem, MyRandom rand, IEvaluator evaluator) {
		
		b
		.set("evaluator", evaluator)
		.set("problem", problem)
		.set("rand", rand);

		return b.build();

	}

}
