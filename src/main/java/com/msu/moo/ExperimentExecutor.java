package com.msu.moo;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.experiment.AbstractExperiment;
import com.msu.moo.util.ObjectFactory;

public class ExperimentExecutor {

	/*
	 * EXERIMENTS AVAILABLE
	 * KursaweExperiment, ZDT1Experiment
	 * 
	 */
	
	//! experiment that should be executed
	protected final static String EXPERIMENT = "com.msu.moo.experiment.impl.KursaweExperiment";
	
	//! number of iterations per experiment
	protected final static int ITERATIONS = 1;
	
	//! max evaluations per run
	protected final static long MAX_EVALUATIONS = 100000;
	
	//! random seed for experiment execution
	protected final static long SEED = 8979654;		
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		AbstractExperiment<?> experiment = ObjectFactory.create(AbstractExperiment.class,  EXPERIMENT);
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
		experiment.report();
	}
	

}
