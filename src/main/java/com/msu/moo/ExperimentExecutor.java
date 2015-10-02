package com.msu.moo;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.experiment.AExperiment;
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
	protected final static int ITERATIONS = 10;
	
	//! max evaluations per run
	protected final static int MAX_EVALUATIONS = 50000;
	
	//! random seed for experiment execution
	protected final static long SEED = 8979654;		
	
	
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		AExperiment experiment = ObjectFactory.create(AExperiment.class,  EXPERIMENT);
		experiment.run(MAX_EVALUATIONS, ITERATIONS, SEED);
	}
	

}
