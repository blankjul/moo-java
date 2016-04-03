package com.msu.moo.mock;

import com.msu.moo.interfaces.IEvolutionaryVariable;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.variable.AVariable;

public class MockVariable extends AVariable<Integer> implements IEvolutionaryVariable<Integer>{


	public MockVariable() {
		super(-1);
	}
	
	public MockVariable(Integer obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new MockVariable(obj);
	}

	@Override
	public MockVariable build(Integer obj) {
		return new MockVariable(obj);
	}
	
	public static MockVariable create() {
		return new MockVariable();
	}
	
}
