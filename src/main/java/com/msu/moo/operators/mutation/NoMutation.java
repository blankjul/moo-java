package com.msu.moo.operators.mutation;

import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Random;

public class NoMutation<T>  extends AbstractMutation<T>{

	@Override
	protected void mutate_(T element, Random rand) {}

}
