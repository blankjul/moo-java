package com.msu.moo.model.variable;
import java.util.ArrayList;
import java.util.List;

public class BooleanListVariable extends ListVariable<List<Boolean>> {

	public BooleanListVariable(List<List<Boolean>> obj) {
		super(obj);
	}

	@Override
	public ListVariable<List<Boolean>> copy() {
		return new BooleanListVariable(new ArrayList<>(obj));
	}

	@Override
	public ListVariable<List<Boolean>> build(List<List<Boolean>> obj) {
		return new BooleanListVariable(obj);
	}
	
	

}
