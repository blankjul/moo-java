package com.msu.moo.experiment;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

public abstract class AExperiment<P extends IProblem, S> {

	static final Logger logger = Logger.getLogger(AExperiment.class);


	// ! all the calculated fronts are saved there
	protected ExperimetSettings<P, S> settings = null;
	protected ExperimentResult<S> result = null;

	public void run(int maxEvaluations, int iterations, long seed) {


		// initialize the algorithms and experiments
		settings = new ExperimetSettings<>();
		setProblems(settings);
		setAlgorithms(settings);
		setOptima(settings);
		settings.setMaxEvaluations(maxEvaluations);
		
		//! result with all the fronts
		result = new ExperimentResult<>(settings);


		logger.info("Running the experiment.");
		// for each problem. the true front could also be null!
		for (P problem : settings.getProblems()) {

			// get the problem
			logger.info("Execute Problem: " + problem.toString());


			// check if run makes sense
			if (settings.getProblems() == null || settings.getAlgorithms() == null)
				throw new RuntimeException("Experiment could not be executed. Either problem or algorithms is null!");

			logger.info("Following Algorithms are used and compared: " + settings.getAlgorithms().toString());
			

			// calculate the result for each algorithm
			for (IAlgorithm<S,P> algorithm : settings.getAlgorithms()) {
				

				logger.info(String.format("Startings runs for %s", algorithm));

				for (int i = 0; i < iterations; i++) {
					
					// set the random seed that the results will be comparable
					Random.getInstance().setSeed(seed + i);
					
					long startTime = System.currentTimeMillis();
					S set = algorithm.run(new Evaluator<P>(problem, maxEvaluations));
					double elapsedTime = (System.currentTimeMillis() - startTime);
	
					logger.info(String.format("Run of %s in %f seconds", algorithm, elapsedTime / 1000.0));
					result.add(problem, algorithm, set);
					
				}
			}
			logger.info("All fronts were calculate and experiment is finished.");
		}
		
		
	}

	/**
	 * @return the result of the experiment. if not executed it will be null.
	 */
	public ExperimentResult<S> getResult() {
		return result;
	}

	public static NonDominatedSolutionSet estimateTrueFront(Collection<NonDominatedSolutionSet> sets) {
		NonDominatedSolutionSet estimatedFront = new NonDominatedSolutionSet();
		for (NonDominatedSolutionSet set : sets) {
			estimatedFront.addAll(set.getSolutions());
		}
		return estimatedFront;
	}

	
	// ! all algorithms that should be evaluated for this experiment
	protected abstract void setAlgorithms(ExperimetSettings<P, S> settings);

	// ! all problem instances that should be solved
	protected abstract void setProblems(ExperimetSettings<P, S> settings);

	//! visualizes the result of the experiment
	public abstract void visualize();
	
	
	//! prints the result of the experiment
	public void report() {
		for (P problem : settings.getProblems()) {
			for (IAlgorithm<S, P> algorithm : settings.getAlgorithms()) {
				for(S set : result.get(problem, algorithm)) {
					report_(problem, algorithm, set);
				}
			}
		}
	}
	
	protected void report_(P problem, IAlgorithm<S, P> algorithm, S set) {
	}
	
	/**
	 * Standard method no pareto front for the problem is given. If this method
	 * is overridden, for EVERY problem should be an value even if null!
	 */
	protected void setOptima(ExperimetSettings<P, S> settings) {
		for (P p : settings.getProblems()) {
			settings.getOptima().put(p, null);
		}
	}
	
	
	

	

}
