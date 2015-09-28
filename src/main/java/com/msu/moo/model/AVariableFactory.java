package com.msu.moo.model;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;

public abstract class AVariableFactory implements IVariableFactory{

	public abstract IVariable next(IProblem problem);

	@Override
	public boolean hasNext() {
		return true;
	}

}
