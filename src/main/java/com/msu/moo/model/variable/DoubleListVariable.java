package com.msu.moo.model.variable;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvolutionaryVariable;
import com.msu.interfaces.IVariable;

public class DoubleListVariable extends AVariable<List<Double>> implements IEvolutionaryVariable<List<Double>, DoubleListVariable>{

	public DoubleListVariable(List<Double> obj) {
		super(obj);
	}

	@Override
	public IVariable copy() {
		return new DoubleListVariable(new ArrayList<>(obj));
	}

	@Override
	public DoubleListVariable build(List<Double> obj) {
		return new DoubleListVariable(obj);
	}
	

}