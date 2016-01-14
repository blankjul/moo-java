package com.msu.interfaces;

import java.util.List;

public interface ICrossover {
	
	public List<IVariable> crossover(IVariable a, IVariable b);

}
