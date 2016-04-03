package com.msu.moo.operators.crossover;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.util.MyRandom;

public class NoCrossover<V extends IEvolutionaryVariable<?>> implements ICrossover<V> {

	@Override
	@SuppressWarnings("unchecked") 
	public List<V> crossover(V a, V b, MyRandom rand) {
		List<V> result = new ArrayList<>();
		result.add((V) a.copy());
		result.add((V) b.copy());
		return result;
	}

}
