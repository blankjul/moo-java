package com.msu.moo.model;

import com.msu.moo.interfaces.IVariable;

public abstract class ASingleObjectiveProblem<V extends IVariable> extends AProblem<V>{

	@Override
	final public int getNumberOfObjectives() {
		return 1;
	}

}
