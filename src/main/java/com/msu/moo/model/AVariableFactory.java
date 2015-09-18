package com.msu.moo.model;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.IVariableFactory;

public abstract class AVariableFactory<V extends IVariable, P extends IProblem>  implements IVariableFactory<V, P>{

	public abstract V next(P problem);

	@Override
	public boolean hasNext() {
		return true;
	}

}
