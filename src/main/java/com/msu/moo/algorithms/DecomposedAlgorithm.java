package com.msu.moo.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.model.AbstractAlgorithm;
import com.msu.model.Evaluator;
import com.msu.moo.algorithms.moead.MOEADUtil;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.soo.ASingleObjectiveAlgorithm;
import com.msu.soo.SingleObjectiveDecomposedProblem;
import com.msu.util.Random;
import com.msu.util.Range;
import com.msu.util.Util;

public class DecomposedAlgorithm extends AbstractAlgorithm {

	// ! number of decomposed problem that are solved
	protected int numOfWeights = 10;

	// ! if range is set the resulting values are normalized
	protected Range<Double> range = null;

	// ! single objective algorithm to execute
	protected List<ASingleObjectiveAlgorithm> algorithms = new ArrayList<>();

	// ! could be filled with commands in subclass
	protected void initialize(IProblem problem, IEvaluator eval, Random rand) {
	};
	
	

	@Override
	public NonDominatedSolutionSet run_(IProblem problem, IEvaluator eval, Random rand) {

		initialize(problem, eval, rand);
		
		// the final result
		NonDominatedSolutionSet set = new NonDominatedSolutionSet();

		// weights to be used for decomposition
		List<List<Double>> weights = MOEADUtil.getUniformDistributedWeights(rand, numOfWeights, 2);

		// for each decomposed weight
		for (List<Double> w : weights) {

			for (ASingleObjectiveAlgorithm a : algorithms) {

				ASingleObjectiveAlgorithm algorithmToExecute = Util.cloneObject(a);

				// solve the single-objective normalized problem
				IProblem singleObjProblem = new SingleObjectiveDecomposedProblem<>(problem, w, range);

				NonDominatedSolutionSet result = algorithmToExecute.run(singleObjProblem,
						new Evaluator(eval.getMaxEvaluations()), rand);

				if (!result.getSolutions().isEmpty()) {
					Solution solutionToAdd = eval.evaluate(problem, result.get(0).getVariable());
					set.add(solutionToAdd);
				} else {
					System.err.println("No feasible solution found!");
				}
			}
		}
		
		return set;

	}

}
