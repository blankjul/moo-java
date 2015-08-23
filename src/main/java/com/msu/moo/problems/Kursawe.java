package com.msu.moo.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.model.AbstractEvaluator;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.variables.DoubleListVariable;

public class Kursawe implements IProblem<DoubleListVariable, Kursawe>{
	

	private class KursaweEvaluator extends AbstractEvaluator<DoubleListVariable, Kursawe> {

		public KursaweEvaluator(Kursawe problem) {
			super(problem);
		}

		@Override
		protected <T> List<Double> evaluate(DoubleListVariable var) {
			
			double aux, xi, xj;
		    double[] fx = new double[2];
		    double[] x = new double[3];
		    x[0] = var.get().get(0);
		    x[1] = var.get().get(1);
		    x[2] = var.get().get(2);
		    
		    if (x[0] < -5.0 || x[0] > 5.0 || x[1] < -5.0 || x[1] > 5.0 || x[2] < -5.0 || x[2] > 5.0) {
		    	new ArrayList<Double>(Arrays.asList(Double.MAX_VALUE, Double.MAX_VALUE));
		    }
		    
		    fx[0] = 0.0;
		    for (int i = 0; i < var.get().size() - 1; i++) {
		      xi = x[i] * x[i];
		      xj = x[i + 1] * x[i + 1];
		      aux = (-0.2) * Math.sqrt(xi + xj);
		      fx[0] += (-10.0) * Math.exp(aux);
		    }

		    fx[1] = 0.0;

		    for (int i = 0; i < var.get().size(); i++) {
		      fx[1] += Math.pow(Math.abs(x[i]), 0.8) +
		        5.0 * Math.sin(Math.pow(x[i], 3.0));
		    }
		    
		    return new ArrayList<Double>(Arrays.asList(fx[0], fx[1]));
		}
	}
	
	
	@Override
	public AbstractEvaluator<DoubleListVariable, Kursawe> getEvaluator() {
		return new KursaweEvaluator(this);
	}



}
