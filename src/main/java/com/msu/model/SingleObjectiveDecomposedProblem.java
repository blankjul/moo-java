package com.msu.model;

import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.algorithms.moead.MOEADUtil;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.Range;

public class SingleObjectiveDecomposedProblem<V extends IVariable> extends AProblem<V>{
	
	protected IProblem problem = null;
	
	protected List<Double> weights = null;
	
	protected Range<Double> range = null;

	public SingleObjectiveDecomposedProblem(IProblem problem, List<Double> weights) {
		if (problem.getNumberOfObjectives() != weights.size()) 
			throw new RuntimeException(String.format("For decomposed problems the number of objectives %s should be equal to the number of weights %s.", problem.getNumberOfObjectives(), weights.size()));
		this.problem = problem;
		this.weights = weights;
		this.name = problem.toString();
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

	@Override
	protected void evaluate_(V var, List<Double> objectives, List<Double> constraintViolations) {
		Solution s = new Evaluator(problem).evaluate(var);
		if (range != null) s = s.normalize(range.get());
		objectives.add(MOEADUtil.calcWeightedSum(s.getObjective(), weights));
		constraintViolations.addAll(s.getConstraintViolations());
	}

	@Override
	public int getNumberOfConstraints() {
		return problem.getNumberOfConstraints();
	}

	public IProblem getProblem() {
		return problem;
	}

	public List<Double> getWeights() {
		return weights;
	}

	public Range<Double> getRange() {
		return range;
	}

	public void setRange(Range<Double> range) {
		this.range = range;
	}
	
	
    
    
    
    

}
