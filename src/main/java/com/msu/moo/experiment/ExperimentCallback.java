package com.msu.moo.experiment;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Util;

public class ExperimentCallback implements Callable<ExperimentCallback> {

	static final Logger logger = Logger.getLogger(ExperimentCallback.class);

	public AExperiment experiment;
	public int i;
	public int j;
	public int k;
	public double duration;
	public NonDominatedSolutionSet set;
	public int maxEvaluations;
	public int iterations;

	public ExperimentCallback(AExperiment experiment, int maxEvaluations, int iterations, int i, int j, int k) {
		super();
		this.experiment = experiment;
		this.maxEvaluations = maxEvaluations;
		this.iterations = iterations;
		this.i = i;
		this.j = j;
		this.k = k;
	}

	public IProblem getProblem() {
		return experiment.problems.get(i);
	}

	public IAlgorithm getAlgorithm() {
		return experiment.algorithms.get(j);
	}

	@Override
	public ExperimentCallback call() throws Exception {

		IAlgorithm a = Util.cloneObject(getAlgorithm());
		IProblem p = Util.cloneObject(getProblem());

		long startTime = System.currentTimeMillis();
		set = a.run(new Evaluator(p, maxEvaluations));
		duration = ((System.currentTimeMillis() - startTime) / 1000.0);

		String prefix = String.format("[%s/%s | %s/%s | %s/%s ]", i + 1, experiment.problems.size(), j + 1, experiment.algorithms.size(), k + 1, iterations);
		logger.info(String.format("%s %s in %f s", prefix, getAlgorithm(), duration));

		return this;
	}

}
