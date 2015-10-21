package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.msu.moo.interfaces.IAlgorithm;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;
import com.msu.moo.util.events.AlgorithmFinishedExecution;
import com.msu.moo.util.events.EventDispatcher;
import com.msu.moo.util.events.ExperimentFininshedEvent;
import com.msu.moo.util.events.ProblemFinishedEvent;
import com.msu.moo.util.events.RunFinishedEvent;

public abstract class AExperiment {

	static final Logger logger = Logger.getLogger(AExperiment.class);

	// ! all problems that are considered for the run
	protected List<IProblem> problems = new ArrayList<>();

	// ! all algorithms which are compared
	protected List<IAlgorithm> algorithms = new ArrayList<>();

	// ! result storage for the pareto fronts
	protected ExperimentResult result = new ExperimentResult();

	public void run(int maxEvaluations, int iterations, long seed) {
		
		// set random seed to created problems for seed equally
		Random.getInstance().setSeed(seed);
		
		// execute the inherited methods
		setProblems(problems);
		setAlgorithms(algorithms);
		run(problems, algorithms, maxEvaluations, iterations, seed);
		
	}

	public void run(List<IProblem> problems, List<IAlgorithm> algorithms, int maxEvaluations, int iterations, long seed) {

		// initialize after problems and algorithms are known
		initialize();

		logger.info("Running the experiment.");
		logger.info("Problems count: " + problems.size());
		logger.info("Algorithms count: " + algorithms.size());

		// for each problem. the true front could also be null!
		for (int i = 0; i < problems.size(); i++) {
			IProblem problem = problems.get(i);

			// get the problem
			logger.info("Execute Problem: " + problem.toString());
			logger.info("Following Algorithms are used and compared: " + algorithms.toString());

			// calculate the result for each algorithm
			for (int j = 0; j < algorithms.size(); j++) {
				IAlgorithm algorithm = algorithms.get(j);

				for (int k = 0; k < iterations; k++) {

					// set the random seed that the results will be comparable
					Random.getInstance().setSeed(seed + k);

					long startTime = System.currentTimeMillis();
					NonDominatedSolutionSet set = algorithm.run(new Evaluator(problem, maxEvaluations));
					double elapsedTime = (System.currentTimeMillis() - startTime);

					String prefix = String.format("[%s/%s | %s/%s | %s/%s]", i + 1, problems.size(), j + 1, algorithms.size(), k+1, iterations);
					logger.info(String.format("%s %s in %f s", prefix, algorithm, elapsedTime / 1000.0));
					result.add(problem, algorithm, set);
					EventDispatcher.getInstance().notify(new RunFinishedEvent(this, problem, algorithm, k, set));
					
				}
				EventDispatcher.getInstance().notify(new AlgorithmFinishedExecution(this, problem, algorithm));
			}
			
			logger.info("All fronts were calculate and experiment is finished.");
			EventDispatcher.getInstance().notify(new ProblemFinishedEvent(this, problem));

		}

		//EventDispatcher.getInstance().notify(new FinishedExperiment(this));
		logger.info("Executing the finalize method of the experiment.");
		EventDispatcher.getInstance().notify(new ExperimentFininshedEvent(this));
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
	


}