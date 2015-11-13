package com.msu.soo;

import com.msu.interfaces.IVariable;
import com.msu.model.AProblem;

public abstract class ASingleObjectiveProblem<V extends IVariable> extends AProblem<V>{

	@Override
	final public int getNumberOfObjectives() {
		return 1;
	}

}
