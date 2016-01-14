package com.msu.soo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.ICrossover;
import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IFactory;
import com.msu.interfaces.IMutation;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.OperatorFactory;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.util.MyRandom;

public class SingleObjectiveEvolutionaryAlgorithm extends ASingleObjectiveAlgorithm {

	// ! size of the whole Population
	protected int populationSize;

	// ! default mutation probability
	protected Double probMutation;

	// ! operator for crossover
	protected OperatorFactory<ICrossover> fCrossover;
	protected ICrossover crossover;

	// ! operator for mutation
	protected OperatorFactory<IMutation> fMutation;
	protected IMutation mutation;

	// ! factory for creating new instances
	protected OperatorFactory<IFactory> fFactory;
	protected IFactory factory;
	
	
	// ! population of the last run
	protected SolutionSet population;

	
	public static Comparator<Solution> comp = new Comparator<Solution>() {
		@Override
		public int compare(Solution o1, Solution o2) {
			int constraint = Double.compare(o1.getMaxConstraintViolation(), o2.getMaxConstraintViolation());
			if (constraint != 0)
				return constraint;
			else {
				for (int i = 0; i < o1.countObjectives(); i++) {
					int value = Double.compare(o1.getObjective(i), o2.getObjective(i));
					if (value != 0)
						return value;
				}
				return 0;
			}
		}
	};

	@Override
	public Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		
		
		this.factory = fFactory.create(problem, rand, evaluator);
		
		// initialize random population
		SolutionSet initial = new SolutionSet(populationSize * 2);
		for (int i = 0; i < populationSize; i++) {
			initial.add(evaluator.evaluate(problem, factory.next()));
		}
		return run__(problem, evaluator, rand, initial);
	}

	public Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand, SolutionSet initialPopulation) {
		
		this.crossover = fCrossover.create(problem, rand, evaluator);
		this.mutation = fMutation.create(problem, rand, evaluator);
		
		this.population = initialPopulation;
		while (evaluator.hasNext()) {
			next(problem, evaluator, rand);
		}
		return population.get(0);
	}

	public void next(IProblem problem, IEvaluator evaluator, MyRandom rand) {

		// mating with random selection of the best 20 percent
		SolutionSet offsprings = new SolutionSet(populationSize);

		// selects per default always the maximal value
		BinaryTournamentSelection selector = new BinaryTournamentSelection(population, new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return -1 * comp.compare(o1, o2);
			}
		}, rand);

		while (offsprings.size() < populationSize) {
			// crossover
			List<IVariable> off = crossover.crossover(selector.next().getVariable(), selector.next().getVariable());
			// mutation
			for (IVariable offspring : off) {
				if (rand.nextDouble() < this.probMutation) {
					mutation.mutate(offspring);
				}
				offsprings.add(evaluator.evaluate(problem, offspring));
			}
		}
		population.addAll(offsprings);

		// eliminate duplicates to ensure variety in the population
		population = new SolutionSet(new HashSet<>(population));
		// truncate the population -> survival of the fittest
		population.sort(comp);
		population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));

	}


	public SolutionSet getPopulation() {
		return population;
	}
	
	public void setPopulation(SolutionSet population) {
		this.population = population;
	}
	

}
