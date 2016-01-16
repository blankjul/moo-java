package com.msu.experiment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IMultiObjectiveAlgorithm;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.Evaluator;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.util.MyRandom;

public abstract class AMultiObjectiveExperiment<V extends IVariable, P extends IProblem<V>> {

	static final Logger logger = Logger.getLogger(AMultiObjectiveExperiment.class);

	// ! all problems that are considered for the run
	protected List<P> problems = new ArrayList<>();

	// ! all algorithms which are compared
	protected List<IMultiObjectiveAlgorithm<V, P>> algorithms = new ArrayList<>();


	public void run(int maxEvaluations, int iterations, long seed) {

		// execute the inherited methods
		setProblems(problems);
		setAlgorithms(algorithms);
		run(problems, algorithms, maxEvaluations, iterations, seed);

	}

	public void run(List<P> problems, List<IMultiObjectiveAlgorithm<V, P>> algorithms, int maxEvaluations,
			int iterations, long seed) {


		logger.info("Running the experiment.");
		logger.info("Problems: " + problems.size());
		logger.info("Algorithms: " + algorithms.size());
		logger.info("Iterations: " + iterations);
		logger.info("MaxEvaluations: " + maxEvaluations);

		// for each problem. the true front could also be null!
		for (int i = 0; i < problems.size(); i++) {

			// calculate the result for each algorithm
			for (int j = 0; j < algorithms.size(); j++) {

				for (int k = 0; k < iterations; k++) {

					P problem = problems.get(i);
					IMultiObjectiveAlgorithm<V, P> algorithm = algorithms.get(j);

					// set the random seed that the results will be comparable
					MyRandom rand = new MyRandom(seed + k);
					IEvaluator evaluator = new Evaluator(maxEvaluations);

					// calculate result
					NonDominatedSolutionSet<V> set = algorithm.run(problem, evaluator, rand);

					// print result
					System.out.println("------------------------------");
					System.out.println(problem);
					System.out.println(algorithm);
					System.out.println(k);
					System.out.println("------------------------------");
					for (Solution<V> s : set) {
						String str = String.format("%f %f", s.getObjective(0), s.getObjective(1)).replaceAll("\\[|\\]", "");
						System.out.println(str);
						//System.out.println(s);
					}
					System.out.println("------------------------------");

				}
			}
		}

	}


	public static <T> NonDominatedSolutionSet<T> estimateTrueFront(Collection<NonDominatedSolutionSet<T>> sets) {
		NonDominatedSolutionSet<T> estimatedFront = new NonDominatedSolutionSet<T>();
		for (NonDominatedSolutionSet<T> set : sets) {
			estimatedFront.addAll(set.getSolutions());
		}
		return estimatedFront;
	}

	// ! all algorithms that should be evaluated for this experiment
	protected abstract void setAlgorithms(List<IMultiObjectiveAlgorithm<V, P>> algorithms);

	// ! all problem instances that should be solved
	protected abstract void setProblems(List<P> problems);



	public List<P> getProblems() {
		return problems;
	}

	public List<IMultiObjectiveAlgorithm<V, P>> getAlgorithms() {
		return algorithms;
	}

}