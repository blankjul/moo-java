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
	protected final static String EXPERIMENT = "com.msu.moo.experiment.impl.ZDT1Experiment";
	
	//! number of iterations per experiment
	protected final static int ITERATIONS = 3;
	
	//! max evaluations per run
	protected final static int MAX_EVALUATIONS = 50000;
	
	//! random seed for experiment execution
	protected final static long SEED = 8979654;		
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		AbstractExperiment<?> experiment = ObjectFactory.create(AbstractExperiment.class,  EXPERIMENT);
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
		experiment.visualize();
	}
	

}
