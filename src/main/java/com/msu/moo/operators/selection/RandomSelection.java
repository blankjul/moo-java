package com.msu.moo.operators.selection;

import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.ASelection;
import com.msu.moo.util.MyRandom;



public class RandomSelection<S extends ISolution<V>, V> extends ASelection<S, V> {

	@Override
	public S next(List<S> population, MyRandom rand) {
		return rand.select(population);
	}

	


}
