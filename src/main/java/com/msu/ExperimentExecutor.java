package com.msu;

import org.apache.log4j.BasicConfigurator;

import com.msu.experiment.AExperiment;
import com.msu.util.ObjectFactory;

/**
 * This here is the central Experiment Executor. This avoids that each
 * experiment contains its own main method.
 * 
 * The experiment is factored by the given string. Furthermore, the iterations,
 * maxEvaluations and the random seed has to be set.
 * 
 * EXERIMENTS AVAILABLE
 * KursaweExperiment ZDTExperiment
 * 
 */
public class ExperimentExecutor {

	// ! experiment that should be executed
	protected final static String EXPERIMENT = "com.msu.experiment.impl.KursaweExperiment";

	// ! number of iterations per experiment
	protected final static int ITERATIONS = 1;

	// ! max evaluations per run
	protected final static int MAX_EVALUATIONS = 50000;

	// ! random seed for experiment execution
	protected final static long SEED = 123456;

	public static void main(String[] args) {
		BasicConfigurator.configure();
		AExperiment experiment = ObjectFactory.create(AExperiment.class, EXPERIMENT);
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
	}

}
