package com.msu.moo.experiment;

import java.util.concurrent.Callable;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.algorithms.IAlgorithm;
import com.msu.moo.model.Evaluator;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;

public class ExperimentCallback<R, V extends IVariable, P extends IProblem<V>> implements Callable<ExperimentCallback<R,V,P>> {
	
	public IAlgorithm<R,V,P> algorithm;
	public P problem;
	public MyRandom rand;
	public int i;
	public int j;
	public int k;
	public int maxEvaluations;
	
	public double duration;
	public R result;
	
	


	public ExperimentCallback(IAlgorithm<R, V, P> algorithm, P problem, MyRandom rand, int i, int j, int k,
			int maxEvaluations) {
		super();
		this.algorithm = algorithm;
		this.problem = problem;
		this.rand = rand;
		this.i = i;
		this.j = j;
		this.k = k;
		this.maxEvaluations = maxEvaluations;
	}




	@Override
	public ExperimentCallback<R,V,P> call() throws Exception {
		
		IAlgorithm<R,V,P> a = Util.cloneObject(algorithm);
		P p = Util.cloneObject(problem);

		long startTime = System.currentTimeMillis();
		result = a.run(p, new Evaluator(maxEvaluations), rand);
		duration = ((System.currentTimeMillis() - startTime) / 1000.0);

		return this;
		
	}
	

}
