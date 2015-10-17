package com.msu.moo.model;

import java.util.ArrayList;
import java.util.Collection;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;

public abstract class AVariableFactory implements IVariableFactory{

	public abstract IVariable next(IProblem problem);

	
	public Collection<IVariable> next(IProblem problem, int n) {
		Collection<IVariable> l = new ArrayList<IVariable>(n);
		for (int i = 0; i < n; i++) {
			l.add(next(problem));
		}
		return l;
	};
	
	
	
	@Override
	public boolean hasNext() {
		return true;
	}

}
