package com.msu.moo.operators.selection;

import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.ASelection;
import com.msu.moo.util.MyRandom;



public class RandomSelection<S extends ISolution<?>> extends ASelection<S> {

	@Override
	public S next(List<S> population, MyRandom rand) {
		return rand.select(population);
	}

	


}
