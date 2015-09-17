package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

public abstract class AbstractExperiment<P extends IProblem> {

	static final Logger logger = Logger.getLogger(AbstractExperiment.class);


	// ! all the calculated fronts are saved there
	protected ExperimetSettings<P> settings = null;
	protected ExperimentResult result = null;

	public void run(int maxEvaluations, int iterations, long seed) {

		// set the random seed that the results will be comparable
		Random.getInstance().setSeed(seed);

		// initialize the algorithms and experiments
		settings = new ExperimetSettings<>();
		setProblems(settings);
		setAlgorithms(settings);
		setTrueFronts(settings);
		
		//! result with all the fronts
		result = new ExperimentResult(settings);


		logger.info("Running the experiment.");
		// for each problem. the true front could also be null!
		for (P problem : settings.getProblems()) {

			// get the problem
			logger.info("Execute Problem: " + problem.toString());


			// check if run makes sense
			if (settings.problems == null || settings.algorithms == null)
				throw new RuntimeException("Experiment could not be executed. Either problem or algorithms is null!");

			logger.info("Following Algorithms are used and compared: " + settings.algorithms.toString());

			// calculate the result for each algorithm
			for (IAlgorithm<P> algorithm : settings.algorithms) {

				logger.info(String.format("Startings runs for %s", algorithm));

				for (int i = 0; i < iterations; i++) {
					NonDominatedSolutionSet set = algorithm.run(new Evaluator<P>(problem, maxEvaluations));
					logger.info(String.format("[%s] Found %s non dominated solutions.", algorithm, set.size()));
					result.add(problem, algorithm, set);
				}
			}
			logger.info("All fronts were calculate and experiment is finished.");
		}
		
		
	}

	/**
	 * @return the result of the experiment. if not executed it will be null.
	 */
	public ExperimentResult getResult() {
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
	protected abstract void setAlgorithms(ExperimetSettings<P> settings);

	// ! all problem instances that should be solved
	protected abstract void setProblems(ExperimetSettings<P> settings);

	
	/**
	 * Standard method no pareto front for the problem is given. If this method
	 * is overridden, for EVERY problem should be an value even if null!
	 */
	protected void setTrueFronts(ExperimetSettings<P> settings) {
		for (P p : settings.getProblems()) {
			settings.mFronts.put(p, null);
		}
	}
	
	public void visualize() {
		for(IVisualize<P> v : getVisualizer()) {
			v.show(settings, result);
		}
	}
	
	
	/**
	 * Standard means no visualization.
	 */
	protected Collection<IVisualize<P>> getVisualizer() {
		return new ArrayList<IVisualize<P>>();
	}

	

}
