package com.msu.moo.operators.mutation;

import java.util.List;

import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;

public class SwapMutation<T, V extends IEvolutionaryVariable<List<T>>> implements IMutation<V> {


	@Override
	public void mutate(V a, MyRandom rand) {
		List<T> l = a.decode();
		final int i = rand.nextInt(0,l.size());
		final int j = rand.nextInt(0,l.size());
		Util.swap(l, i, j);
	}
	
	
}
