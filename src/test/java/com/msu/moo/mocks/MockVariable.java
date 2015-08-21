package com.msu.moo.mocks;

import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;

public class MockVariable extends AbstractVariable<Integer> {

	
	public MockVariable() {
		super(null);
	}
	
	public MockVariable(Integer obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new MockVariable(obj);
	}

}
