package com.msu.moo.operators.mutation;

import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;

public class NoMutation<V extends IEvolutionaryVariable<?>> implements IMutation<V> {

	@Override
	public void mutate(V a, MyRandom rand) {
	}

}
