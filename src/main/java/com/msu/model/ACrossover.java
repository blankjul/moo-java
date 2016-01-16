package com.msu.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IEvolutionaryVariable;
import com.msu.util.MyRandom;

public abstract class ACrossover<T, V extends IEvolutionaryVariable<T,V>> implements ICrossover<V> {


	public abstract List<T> crossover(T a, T b, MyRandom rand);
	

	@Override
	public List<V> crossover(V a, V b, MyRandom rand) {
		
		List<T> offspring = crossover(a.decode(), b.decode(), rand);
		
		List<V> result = new ArrayList<>();
		
		for (T off : offspring) {
			V var = a.build(off);
			result.add(var);
		}
	
		return result;
	}

	
	
	
}
