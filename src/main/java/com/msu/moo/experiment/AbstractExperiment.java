package com.msu.moo.experiment;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

public abstract class AbstractExperiment<P extends IProblem> {
	
	// ! all algorithms that should be evaluated for this experiment
	protected abstract List<IAlgorithm<P>> getAlgorithms();

	// ! all problem instances that should be solved
	protected abstract Map<P, NonDominatedSolutionSet> getProblems();
	
	// ! visualize the results of the experiment - depends on the experiment itself
	public abstract void report();

	
	static final Logger logger = Logger.getLogger(AbstractExperiment.class);

	// algorithm that should be compared in this experiment
	protected List<IAlgorithm<P>> algorithms = null;
	
	// ! problem which should be solved
	protected Map<P, NonDominatedSolutionSet> problems = null;

	// ! all the calculated fronts are saved there
	protected ExperimentResult expResult = null;
	
	
	public void run(int maxEvaluations, int iterations, long seed) {

		// set the random seed that the results will be comparable 
		Random.getInstance().setSeed(seed);
		
		// initialize the algorithms and experiments
		expResult = new ExperimentResult();
		algorithms = getAlgorithms();
		problems = getProblems();

		logger.info("Running the experiment.");
		
		// for each problem. the true front could also be null!
		for (Map.Entry<P, NonDominatedSolutionSet> entry : problems.entrySet()) {
			
			// get the problem
			P problem = entry.getKey();
			logger.info("Execute Problem: " + problem.toString());
			
			
			// store the true front even if it's null
			expResult.addTrueFront(problem, entry.getValue());

			if (problems == null || algorithms == null || algorithms.size() == 0)
				throw new RuntimeException("Experiment could not be executed. Either problem or algorithms is null!");


			logger.info("Following Algorithms are used and compared: " + algorithms.toString());

			// calculate the result for each algorithm
			for (IAlgorithm<P> algorithm : algorithms) {

				logger.info(String.format("Startings runs for %s", algorithm));

				for (int i = 0; i < iterations; i++) {
					NonDominatedSolutionSet set = algorithm.run(new Evaluator<P>(problem, maxEvaluations));
					logger.info(String.format("[%s] Found %s non dominated solutions.", algorithm, set.size()));
					expResult.add(problem, algorithm, set);
				}
			}
			logger.info("All fronts were calculate and experiment is finished.");
		}
	}
	
	/**
	 * @return the result of the experiment.
	 * if not executed it will be null.
	 */
	public ExperimentResult getResult() {
		return expResult;
	}
	
	public static NonDominatedSolutionSet estimateTrueFront(Collection<NonDominatedSolutionSet> sets) {
		NonDominatedSolutionSet estimatedFront = new NonDominatedSolutionSet();
		for(NonDominatedSolutionSet set : sets) {
			estimatedFront.addAll(set.getSolutions());
		}
		return estimatedFront;
	}
	
	
	
	// return the true front - null if unknown
	protected NonDominatedSolutionSet getTrueFront() {
		return null;
	}

	

}
