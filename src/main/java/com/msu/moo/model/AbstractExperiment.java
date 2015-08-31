package com.msu.moo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.msu.moo.model.interfaces.IAlgorithm;
import com.msu.moo.model.interfaces.IExperiment;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.util.Random;

public abstract class AbstractExperiment<P extends IProblem> implements IExperiment {

	static final Logger logger = Logger.getLogger(AbstractExperiment.class);

	// ! all algorithms that should be evaluated for this experiment
	protected abstract void setAlgorithms();

	// ! all problem instances that should be solved
	protected abstract void setProblem();

	
	
	// algorithm that should be compared in this experiment
	protected List<IAlgorithm<P>> algorithms = new ArrayList<>();

	// ! problem which should be solved
	protected P problem = null;

	// ! number of iterations for each algorithm
	abstract public int getIterations();

	// ! number of evaluations as stopping criterion
	abstract public long getMaxEvaluations();

	public Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> run() {
		
		// initialize values
		setProblem();
		setAlgorithms();
		Random.getInstance().setSeed(getSeed());

		if (problem == null || algorithms == null || algorithms.size() == 0)
			throw new RuntimeException("Experiment could not be executed. Either problem or algorithms is null!");

		logger.info("Running the experiment.");
		logger.info("Execute Problem: " + problem.toString());

		logger.info("Following Algorithms are used and compared: " + algorithms.toString());

		NonDominatedSolutionSet trueFront = getTrueFront();
		logger.info(String.format("True Front is known: %s", trueFront != null));

		Map<IAlgorithm<P>, List<NonDominatedSolutionSet>> fronts = new HashMap<>();

		// calculate the result for each algorithm
		for (IAlgorithm<P> algorithm : algorithms) {

			logger.info(String.format("Startings runs for %s", algorithm));
			algorithm.setMaxEvaluations(getMaxEvaluations());

			List<NonDominatedSolutionSet> sets = new ArrayList<>();
			for (int i = 0; i < getIterations(); i++) {
				sets.add(algorithm.run(problem));
			}
			fronts.put(algorithm, sets);
		}
		logger.info("All fronts were calculate and experiment is finished.");
		return fronts;
	}

	/**
	 * If the true front is known you have to override this method. DEFAULT:
	 * Front is not known and therefore this method returns null.
	 */
	@Override
	public NonDominatedSolutionSet getTrueFront() {
		return null;
	}

	public List<IAlgorithm<P>> getAlgorithms() {
		return algorithms;
	}

	public P getProblem() {
		return problem;
	}
	
	
	

}
