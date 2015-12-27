package com.msu.operators.mutation;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.util.MyRandom;
import com.msu.util.Util;

public class NoMutation<T>  extends AbstractMutation<T>{

	@Override
	public T mutate_(T element, IProblem problem, MyRandom rand, IEvaluator evaluator) {
		return Util.cloneObject(element);
	}

}
