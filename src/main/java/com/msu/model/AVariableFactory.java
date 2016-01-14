package com.msu.model;

import com.msu.interfaces.IFactory;
import com.msu.operators.AbstractOperator;

public abstract class AVariableFactory extends AbstractOperator implements IFactory {

	@Override
	public boolean hasNext() {
		return true;
	}
	
	
	
}
