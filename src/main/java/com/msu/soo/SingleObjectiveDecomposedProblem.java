package com.msu.soo;

import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.algorithms.moead.MOEADUtil;
import com.msu.moo.model.solution.Solution;
import com.msu.util.Range;

public class SingleObjectiveDecomposedProblem<V extends IVariable> extends ASingleObjectiveProblem<V> {

	// ! the problem it self which is decomposed
	protected IProblem problem = null;

	// ! the weights to decompose the problem
	protected List<Double> weights = null;

	// ! range that is used for normalizing before
	// ! the objectives are weighted
	protected Range<Double> range = null;

	

	public SingleObjectiveDecomposedProblem(IProblem problem, List<Double> weights) {
		if (problem.getNumberOfObjectives() != weights.size())
			throw new RuntimeException(String.format(
					"For decomposed problems the number of objectives %s should be equal to the number of weights %s.",
					problem.getNumberOfObjectives(), weights.size()));
		this.problem = problem;
		this.weights = weights;
		this.name = problem.toString();
	}
	
	public SingleObjectiveDecomposedProblem(IProblem problem, List<Double> weights, Range<Double> range) {
		this(problem, weights);
		this.range = range;
	}
	
	

	@Override
	protected void evaluate_(V var, List<Double> objectives, List<Double> constraintViolations) {
		Solution s = problem.evaluate(var);
		if (range != null)
			s = s.normalize(range.get());
		objectives.add(MOEADUtil.calcWeightedSum(s.getObjectives(), weights));
		constraintViolations.addAll(s.getConstraintViolations());
	}

	@Override
	public int getNumberOfConstraints() {
		return problem.getNumberOfConstraints();
	}

	public IProblem getMultiObjectiveProblem() {
		return problem;
	}

	public List<Double> getWeights() {
		return weights;
	}
	
	

}
