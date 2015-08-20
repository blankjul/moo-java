package com.msu.moo.model;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;


public abstract class AbstractAlgorithm<V extends IVariable<?>, P extends IProblem<V,P>> implements IAlgorithm<V,P> {
	
}
