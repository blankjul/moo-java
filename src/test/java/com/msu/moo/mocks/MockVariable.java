package com.msu.moo.mocks;

import java.util.List;

import com.msu.interfaces.IVariable;
import com.msu.model.Variable;

public class MockVariable extends Variable<List<Integer>> {

	
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
