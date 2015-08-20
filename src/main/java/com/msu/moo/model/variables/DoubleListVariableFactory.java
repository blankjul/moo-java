package com.msu.moo.model.variables;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.interfaces.IFactory;
import com.msu.moo.util.Random;

public class DoubleListVariableFactory implements IFactory<DoubleListVariable>{
	
	//! length of the vector
	protected int length;
	
	//! length of the vector
	protected double[] range = new double[]{ Double.MIN_VALUE, Double.MAX_VALUE};
		

	
	public DoubleListVariableFactory(int length) {
		super();
		this.length = length;
	}
	
	
	public DoubleListVariableFactory(int length, double[] range) {
		super();
		this.length = length;
		this.range = range;
	}





	@Override
	public DoubleListVariable create() {
		List<Double> l = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			l.add(Random.getInstance().nextDouble(range[0], range[1]));
		}
		return new DoubleListVariable(l);
	}
	

}
