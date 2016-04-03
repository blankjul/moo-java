package com.msu.moo.model.evaluator;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.SolutionSet;

/**
 * This evaluator has a look at the population over time.
 * 
 * - convergencePeriod: Please define the convergencePeriod which means what
 * populations are compared.
 * 
 * - firtN: Also the parameter firtN could observe not only the best but also a
 * sub population.
 * 
 * - percentage: The percentage define the improvement which has to be fulfilled
 * in order to be converged.
 *
 */
public class SingleObectiveConvergenceEvaluator extends AEvaluator {

	// logs the average objectives over time
	protected List<Double> objectives = new ArrayList<Double>();

	// ! how often the same result has to be notified to converge
	protected int convergencePeriod = 1;

	// ! only first N solutions of notifications are observed
	protected int firstN = 1;

	// percentage to improve for convergence
	protected double percentage = 0.001;
	

	public SingleObectiveConvergenceEvaluator() {
		super();
	}

	
	public SingleObectiveConvergenceEvaluator(int convergencePeriod, int firstN, double percentage) {
		super();
		this.convergencePeriod = convergencePeriod;
		this.firstN = firstN;
		this.percentage = percentage;
	}



	@Override
	public boolean hasNext() {

		if (objectives.isEmpty())
			return true;

		// get last average of objectives
		double current = objectives.get(objectives.size() - 1);
		
		// compare with entry of convergencePeriod populations ago
		double lastToCompare = objectives.get(Math.max(0, objectives.size() - 1 - convergencePeriod));

		// if improvement was less than percentage of the current one, go for it
		return (lastToCompare - current) < current * percentage;
	}

	@Override
	public <S extends ISolution<?>> void notify(SolutionSet<S> set) {

		List<S> solutions = set.subList(0, Math.min(set.size(), firstN));

		double avg = 0;
		for (S s : solutions) {
			avg += s.getObjective(0);
		}
		avg /= solutions.size();

		objectives.add(avg);

	}

	@Override
	public Integer getMaxEvaluations() {
		return Integer.MAX_VALUE;
	}

}
