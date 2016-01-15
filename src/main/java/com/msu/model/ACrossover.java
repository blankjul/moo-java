package com.msu.model;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IEvolutionaryVariable;
import com.msu.interfaces.IProblem;
import com.msu.util.MyRandom;

public abstract class ACrossover<T, V extends IEvolutionaryVariable<T,V>> implements ICrossover<V,IProblem<V>> {


	public abstract List<T> crossover(IProblem<V> problem, MyRandom rand, T a, T b);
	

	@Override
	public List<V> crossover(IProblem<V> problem, MyRandom rand,
			V a, V b) {
		
		List<T> offspring = crossover(problem, rand, a.decode(), b.decode());
		
		List<V> result = new ArrayList<>();
		
		for (T off : offspring) {
			V var = a.build(off);
			result.add(var);
		}
	
		return result;
	}

	
	
	
}
