package com.msu.operators.mutation;

import com.msu.moo.util.Random;
import com.msu.operators.AbstractMutation;

public class NoMutation<T>  extends AbstractMutation<T>{

	@Override
	protected void mutate_(T element, Random rand) {}

}
