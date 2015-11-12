package com.msu.model;

import java.util.ArrayList;
import java.util.Collection;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.interfaces.IVariableFactory;
import com.msu.moo.util.Random;

public abstract class AVariableFactory implements IVariableFactory{

	
	public Collection<IVariable> next(IProblem problem, Random rand, int n) {
		Collection<IVariable> l = new ArrayList<IVariable>(n);
		for (int i = 0; i < n; i++) {
			l.add(next(problem, rand));
		}
		return l;
	};
	
	
	
	@Override
	public boolean hasNext() {
		return true;
	}

}
