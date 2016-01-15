package com.msu;

import com.msu.interfaces.IEvolutionaryVariable;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.variable.AVariable;

public class MockVariable extends AVariable<Integer> implements IEvolutionaryVariable<Integer, MockVariable>{


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
