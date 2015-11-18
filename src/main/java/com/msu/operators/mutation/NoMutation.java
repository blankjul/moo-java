package com.msu.operators.mutation;

import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.util.Random;

public class NoMutation<T>  extends AbstractMutation<T>{

	@Override
	protected T mutate_(T element, IProblem problem, Random rand) {
		return element;
	}

}
