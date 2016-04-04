package com.msu.moo.problems.ZDT;
import java.util.List;

import com.msu.moo.model.AProblem;
import com.msu.moo.model.variable.BooleanListVariable;
import com.msu.moo.util.exceptions.EvaluationException;

public class ZDT5 extends AProblem<BooleanListVariable> {

	@Override
	public int getNumberOfConstraints() {
		return 0;
	}

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	protected void evaluate_(BooleanListVariable var, List<Double> objectives, List<Double> constraintViolations) {
		
		if (var.decode().size() != 11) 
			throw new EvaluationException(String.format("There are 11 variables needed for ZDT5, but there are only %s.", var.decode().size()));
		
		if (var.decode().get(0).size() != 30) {
			throw new EvaluationException(String.format("First boolean vector must have 30 elements but has %s", var.decode().get(0).size()));
		}
			
		
		List<Boolean> x1 = var.decode().get(0);
		double f1 = 1 + u(x1);
		
		int g = 0;
		for (int i = 1; i < var.decode().size(); i++) {
			int xu = u(var.decode().get(i));
			if (xu < 5) {
				g += 2 + xu;
			} else if (xu == 5) {
				g += 1;
			}
		}
		
		double f2 = g / (double) f1;
		
		objectives.add(f1);
		objectives.add(f2);
		
	}
	
	public static int u(List<Boolean> b) {
		int sum = 0;
		for(Boolean entry : b) {
			if (entry) sum += 1;
		}
		return sum;
	}

}
