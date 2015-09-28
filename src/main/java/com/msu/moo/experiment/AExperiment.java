package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

public abstract class AExperiment {

	static final Logger logger = Logger.getLogger(AExperiment.class);

	protected List<IProblem> problems = new ArrayList<>();
	protected List<IAlgorithm> algorithms = new ArrayList<>();
	protected Map<IProblem, NonDominatedSolutionSet> mOptima = new HashMap<IProblem, NonDominatedSolutionSet>();
	protected ExperimentResult result = new ExperimentResult();


	public void run(int maxEvaluations, int iterations, long seed) {

		setProblems(problems);
		setAlgorithms(algorithms);
		setOptima(problems, mOptima);


		logger.info("Running the experiment.");
		// for each problem. the true front could also be null!
		for (IProblem problem : problems) {

			// get the problem
			logger.info("Execute Problem: " + problem.toString());
			logger.info("Following Algorithms are used and compared: " + algorithms.toString());

			// calculate the result for each algorithm
			for (IAlgorithm algorithm : algorithms) {

				logger.info(String.format("Startings runs for %s", algorithm));

				for (int i = 0; i < iterations; i++) {

					// set the random seed that the results will be comparable
					Random.getInstance().setSeed(seed + i);

					long startTime = System.currentTimeMillis();
					NonDominatedSolutionSet set = algorithm.run(new Evaluator(problem, maxEvaluations));
					double elapsedTime = (System.currentTimeMillis() - startTime);

					logger.info(String.format("Run of %s in %f seconds", algorithm, elapsedTime / 1000.0));
					result.add(problem, algorithm, set);

				}
			}
			logger.info("All fronts were calculate and experiment is finished.");
		}
		
		logger.info("Executing the finalize method of the experiment.");
		finalize();

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
	protected abstract void setAlgorithms(List<IAlgorithm> algorithms);

	// ! all problem instances that should be solved
	protected abstract void setProblems(List<IProblem> problems);

	//! this method is called after the experiment and could be overridden for
	//! problem specific behavior.
	protected void finalize() {};


	/**
	 * Standard method no pareto front for the problem is given. If this method
	 * is overridden, for EVERY problem should be an value even if null!
	 */
	protected void setOptima(List<IProblem> problems, Map<IProblem, NonDominatedSolutionSet> mOptima) {
		for (IProblem p : problems) {
			mOptima.put(p, null);
		}
	}

	public List<IProblem> getProblems() {
		return problems;
	}

	public List<IAlgorithm> getAlgorithms() {
		return algorithms;
	}

	public Map<IProblem, NonDominatedSolutionSet> getOptima() {
		return mOptima;
	}
	
	

}
