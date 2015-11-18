package com.msu.soo;

import java.util.Comparator;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.interfaces.IVariableFactory;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.AbstractMutation;
import com.msu.operators.selection.RandomSelection;
import com.msu.util.Random;

public class SingleObjectiveEvolutionaryAlgorithm extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! operator for crossover
	protected AbstractCrossover<?> crossover;

	// ! operator for mutation
	protected AbstractMutation<?> mutation;

	// ! factory for creating new instances
	protected IVariableFactory factory;

	
	@Override
	public Solution run__(IProblem problem, IEvaluator evaluator, Random rand) {
		
		// initialize random population
        SolutionSet population = new SolutionSet(populationSize * 2);
		for (IVariable variable : factory.next(problem, rand, populationSize)) {
			population.add(evaluator.evaluate(problem, variable));
		}
		
		while (evaluator.hasNext()) {
			
			// mating with random selection
			SolutionSet offsprings = new SolutionSet(populationSize);
			RandomSelection selector = new RandomSelection(population, rand);
			while (offsprings.size() < populationSize) {
				// crossover
				List<IVariable> off = crossover.crossover(selector.next().getVariable(), selector.next().getVariable(), rand);
				// mutation
				for (IVariable offspring : off) {
					if (rand.nextDouble() < this.probMutation) {
						offspring = mutation.mutate(offspring, problem, rand);
					}
					offsprings.add(evaluator.evaluate(problem, offspring));
				}
			}
			population.addAll(offsprings);
			
			// truncate the population -> survival of the fittest
			population.sort(new Comparator<Solution>() {
				@Override
				public int compare(Solution o1, Solution o2) {
					return Double.compare(o1.getObjectives(0), o2.getObjectives(0));
				}
			});
			population = new SolutionSet(population.subList(0, populationSize));
			
		}
		
		return population.get(0);
	}
	

}
