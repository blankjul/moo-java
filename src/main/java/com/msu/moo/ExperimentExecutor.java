package com.msu.moo;

import org.apache.log4j.BasicConfigurator;

import com.msu.moo.experiment.AExperiment;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.evaluator.StandardEvaluator;
import com.msu.moo.util.ObjectFactory;

/**
 * This is the experiment executor which allows to set some settings. Also
 * multiple threads could be started an therefore the executing time could be
 * decreased.
 * 
 * 
 * EXERIMENTS AVAILABLE
 * 
 * KursaweExperiment ZDTExperiment
 * 
 */
public class ExperimentExecutor {

	// ! experiment that should be executed
	protected final static String EXPERIMENT = "com.msu.moo.experiment.impl.NSGAIIExperiment";

	// ! number of iterations per experiment
	protected final static int ITERATIONS = 100;

	// ! max evaluations per run
	protected final static IEvaluator EVALUATOR = new StandardEvaluator(10000);

	// ! random seed for experiment execution
	protected final static long SEED = 123; 
	
	// ! allows to use multiple threads
	protected final static int NUM_OF_THREADS = 20;
	
	//! if true the log of the experiment is shown
	protected final static boolean showLog = false;
	

	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) {
		BasicConfigurator.configure();
		AExperiment<?, IVariable, IProblem<IVariable>> experiment = ObjectFactory.create(AExperiment.class, EXPERIMENT);
		experiment.setVerbose(showLog);
		experiment.run(EVALUATOR, ITERATIONS, SEED, NUM_OF_THREADS);
	}

}
