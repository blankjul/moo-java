package com.msu.operators.mutation;

import com.msu.operators.AbstractMutation;
import com.msu.util.Random;

public class NoMutation<T>  extends AbstractMutation<T>{

	@Override
	protected void mutate_(T element, Random rand) {}

}
