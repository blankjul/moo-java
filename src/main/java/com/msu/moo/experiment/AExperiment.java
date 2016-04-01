package com.msu.moo.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.interfaces.ICallback;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Util;

/**
 * This is a general experiment class which could be used to execute single and
 * multi-objective experiments. To experiment class needs to overwrite the
 * analyze method which gets the result from the algorithm.
 * 
 *
 * @param <R>
 *            result type of algorithm
 */
public abstract class AExperiment<R, V extends IVariable, P extends IProblem<V>> {

	// ! logger for print status
	static final Logger logger = Logger.getLogger(AExperiment.class);

	// ! return the callback which is executed when on run finished
	protected abstract ICallback<R, V, P> getCallback();

	// ! all algorithms that should be evaluated for this experiment
	protected abstract void setAlgorithms(P problem, List<IAlgorithm<R, V, P>> algorithms);

	// ! all problem instances that should be solved
	protected abstract void setProblems(List<P> problems);

	
	// ! run the experiment
	public void run(IEvaluator evaluator, int iterations, long seed, int numOfThreads) {
		if (numOfThreads == 1) runSingle(evaluator, iterations, seed);
		else runMulti(evaluator, iterations, seed, numOfThreads);
	}
	
	
	public void runSingle(IEvaluator evaluator, int iterations, long seed) {

		logger.info("Running the experiment.");
		logger.info("Iterations: " + iterations);

		// get the problems to solve
		List<P> problems = new ArrayList<>();
		setProblems(problems);
		logger.info("Problems: " + problems);

		// for each problem. the true front could also be null!
		for (int i = 0; i < problems.size(); i++) {

			// get the algorithms to solve
			List<IAlgorithm<R, V, P>> algorithms = new ArrayList<>();
			setAlgorithms(problems.get(i), algorithms);

			// calculate the result for each algorithm
			for (int j = 0; j < algorithms.size(); j++) {

				for (int k = 0; k < iterations; k++) {

					P problem = problems.get(i);
					IAlgorithm<R, V, P> algorithm = algorithms.get(j);

					// set the random seed that the results will be comparable
					MyRandom rand = new MyRandom(seed + k);

					// calculate result
					R result = algorithm.run(problem, Util.cloneObject(evaluator), rand);

					ICallback<R, V, P> callback = getCallback();
					if (callback!= null) callback.analyze(problem, algorithm, k, result);

				}
			}
		}

	}

	
	
	public void runMulti(IEvaluator evaluator, int iterations, long seed, int numOfThreads) {

		logger.info("Running the experiment.");
		logger.info("Iterations: " + iterations);

		// get the problems to solve
		List<P> problems = new ArrayList<>();
		setProblems(problems);
		logger.info("Problems: " + problems);

		List<Future<ExperimentCallback<R, V, P>>> futures = new ArrayList<>();
		ExecutorService executor = Executors.newScheduledThreadPool(numOfThreads);

		// for each problem. the true front could also be null!
		for (int i = 0; i < problems.size(); i++) {

			// get the algorithms to solve
			List<IAlgorithm<R, V, P>> algorithms = new ArrayList<>();
			setAlgorithms(problems.get(i), algorithms);

			// calculate the result for each algorithm
			for (int j = 0; j < algorithms.size(); j++) {

				for (int k = 0; k < iterations; k++) {

					// set the random seed that the results will be comparable
					MyRandom rand = new MyRandom(seed + k);

					// add to the thread queue
					ExperimentCallback<R, V, P> c = new ExperimentCallback<>(algorithms.get(j), problems.get(i), rand,
							i, j, k, Util.cloneObject(evaluator));
					Future<ExperimentCallback<R, V, P>> future = executor.submit(c);
					futures.add(future);

				}
			}
		}

		try {
			for (Future<ExperimentCallback<R, V, P>> f : futures) {
				ExperimentCallback<R, V, P> c = f.get();

				//String prefix = String.format("[%s/%s | %s | %s/%s ]", c.i + 1, problems.size(), c.algorithm, c.k + 1, iterations);
				//logger.info(String.format("%s %s in %f s", prefix, c.algorithm, c.duration));
				
				ICallback<R, V, P> callback = getCallback();
				if (callback!= null) callback.analyze(c.problem, c.algorithm, c.k, c.result);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		executor.shutdown();

		logger.info("Executing the finalize method of the experiment.");

	}

}