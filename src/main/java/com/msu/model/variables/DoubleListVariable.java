package com.msu.model.variables;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IVariable;

public class DoubleListVariable extends ListVariable<Double>{

	public DoubleListVariable(List<Double> list) {
		super(list);
	}

	@Override
	public IVariable copy() {
		return new DoubleListVariable(new ArrayList<>(obj));
	}


	
	
	
}
