package com.msu.moo.mocks;

import java.util.List;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.AbstractVariable;

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
