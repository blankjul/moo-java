package com.msu.moo.mocks;

import java.util.List;

import com.msu.moo.model.AbstractVariable;
import com.msu.moo.model.interfaces.IVariable;

public class MockVariable extends AbstractVariable<List<Integer>> {

	
	public MockVariable() {
		super(null);
	}
	
	public MockVariable(List<Integer> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new MockVariable(obj);
	}

}
