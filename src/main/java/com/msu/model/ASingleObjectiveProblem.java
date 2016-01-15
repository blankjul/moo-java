package com.msu.model;

import com.msu.interfaces.IVariable;

public abstract class ASingleObjectiveProblem<V extends IVariable> extends AProblem<V>{

	@Override
	final public int getNumberOfObjectives() {
		return 1;
	}

}
