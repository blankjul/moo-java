package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.msu.interfaces.IAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;
import com.msu.moo.util.events.impl.AlgorithmFinishedExecution;
import com.msu.moo.util.events.impl.EventDispatcher;
import com.msu.moo.util.events.impl.ExperimentFininshedEvent;
import com.msu.moo.util.events.impl.ProblemFinishedEvent;
import com.msu.moo.util.events.impl.RunFinishedEvent;

public abstract class AExperiment {

	static final Logger logger = Logger.getLogger(AExperiment.class);

	// ! all problems that are considered for the run
	protected List<IProblem> problems = new ArrayList<>();

	// ! all algorithms which are compared
	protected List<IAlgorithm> algorithms = new ArrayList<>();

	// ! result storage for the pareto fronts
	protected ExperimentResult result = new ExperimentResult();

	public void run(int maxEvaluations, int iterations, long seed) {

		// execute the inherited methods
		setProblems(problems);
		setAlgorithms(algorithms);
		run(problems, algorithms, maxEvaluations, iterations, seed);

	}

	public void run(List<IProblem> problems, List<IAlgorithm> algorithms, int maxEvaluations, int iterations, long seed) {

		// initialize after problems and algorithms are known
		initialize();

		logger.info("Running the experiment.");
		logger.info("Problems: " + problems.size());
		logger.info("Algorithms: " + algorithms.size());
		logger.info("Iterations: " + iterations);
		logger.info("MaxEvaluations: " + maxEvaluations);

		List<Future<ExperimentCallback>> futures = new ArrayList<>();
		ExecutorService executor = Executors.newScheduledThreadPool(8);

		// for each problem. the true front could also be null!
		for (int i = 0; i < problems.size(); i++) {

			// calculate the result for each algorithm
			for (int j = 0; j < algorithms.size(); j++) {

				for (int k = 0; k < iterations; k++) {

					// set the random seed that the results will be comparable
					Random rand = new Random(seed + k);

					// add to the thread queue
					Future<ExperimentCallback> future = executor.submit(new ExperimentCallback(this, rand, maxEvaluations, iterations, i, j, k));
					futures.add(future);

				}
			}
		}

		try {
			for (Future<ExperimentCallback> f : futures) {
				ExperimentCallback callback = f.get();

				IProblem problem = callback.getProblem();
				IAlgorithm algorithm = callback.getAlgorithm();
				NonDominatedSolutionSet set = callback.set;

				result.add(problem, algorithm, set);

				// always notify the RunFinishedExperiment
				EventDispatcher.getInstance().notify(new RunFinishedEvent(this, problem, algorithm, callback.k, set));

				// check if all executions of algorithm is finished
				if (result.get(callback.getProblem(), callback.getAlgorithm()).size() == iterations) {
					EventDispatcher.getInstance().notify(new AlgorithmFinishedExecution(this, problem, algorithm));

					// check if all algorithms are executed for this problem
					if (algorithm.equals(algorithms.get(algorithms.size() - 1))) {
						NonDominatedSolutionSet trueFront = callback.getProblem().getOptimum();
						if (trueFront == null) {
							trueFront = estimateTrueFront(result.get(callback.getProblem(), getAlgorithms()));
						}
						EventDispatcher.getInstance().notify(new ProblemFinishedEvent(this, problem, iterations, trueFront));
					}
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		executor.shutdown();

		// EventDispatcher.getInstance().notify(new FinishedExperiment(this));
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