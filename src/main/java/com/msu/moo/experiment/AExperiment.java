package com.msu.moo.experiment;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IReport;
import com.msu.moo.interfaces.IVisualize;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

public abstract class AExperiment {

	static final Logger logger = Logger.getLogger(AExperiment.class);

	//! all problems that are considered for the run
	protected List<IProblem> problems = new ArrayList<>();
	
	//! all algorithms which are compared
	protected List<IAlgorithm> algorithms = new ArrayList<>();
	
	//! result storage for the pareto fronts
	protected ExperimentResult result = new ExperimentResult();

	//! output directory if results should be written
	protected String outputDir = null;
	
	//! if true the windows are opened to show the plots
	protected boolean visualize = true;
	
	//! list of all visualizer
	protected List<IVisualize> visualizer = new ArrayList<>();
	
	//! list of all reporter
	protected List<IReport> reporter = new ArrayList<>();
	
	
	public void run(int maxEvaluations, int iterations, long seed) {
		setProblems(problems);
		setAlgorithms(algorithms);
		run(problems, algorithms, maxEvaluations, iterations, seed);
	}

	
	public void run(List<IProblem> problems, List<IAlgorithm> algorithms, int maxEvaluations, int iterations, long seed) {

		// initialize after problems and algorithms are known
		initialize();

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

	// ! initialize method which could be overridden
	protected void initialize() {
	};

	// ! this method is called after the experiment and could be overridden for
	// ! problem specific behavior.
	protected void finalize() {
	};

	public List<IProblem> getProblems() {
		return problems;
	}

	public List<IAlgorithm> getAlgorithms() {
		return algorithms;
	}


	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	
	public boolean hasOutputDirectory() {
		return this.outputDir != null && Files.isDirectory(Paths.get(outputDir));
	}

	public boolean isVisualize() {
		return visualize;
	}

	public void setVisualize(boolean visualize) {
		this.visualize = visualize;
	}
	
	
	

}
