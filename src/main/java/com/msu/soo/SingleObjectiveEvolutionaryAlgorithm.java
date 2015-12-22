package com.msu.soo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import com.msu.interfaces.IEvaluator;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.interfaces.IVariableFactory;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.AbstractMutation;
import com.msu.operators.selection.BinaryTournamentSelection;
import com.msu.util.MyRandom;

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
	
	//! population of the last run
	protected SolutionSet population;
	
	
	public static Comparator<Solution> comp = new Comparator<Solution>() {
		@Override
		public int compare(Solution o1, Solution o2) {
			int constraint = Double.compare(o1.getMaxConstraintViolation(), o2.getMaxConstraintViolation());
			if (constraint != 0) return constraint;
			else {
				for (int i = 0; i < o1.countObjectives(); i++) {
					 int value = Double.compare(o1.getObjectives(i), o2.getObjectives(i));
					 if (value != 0) return value;
				}
				return 0;
			}
		}
	};

	
	@Override
	public Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand) {
		// initialize random population
		SolutionSet initial = new SolutionSet(populationSize * 2);
		for (IVariable variable : factory.next(problem, rand, populationSize)) {
			initial.add(evaluator.evaluate(problem, variable));
		}
		return run__(problem, evaluator, rand, initial);
	}
		
		
	public Solution run__(IProblem problem, IEvaluator evaluator, MyRandom rand, SolutionSet initialPopulation) {
		
		this.population = initialPopulation;
		
		sortBySingleObjective(population);
		
		while (evaluator.hasNext()) {
			
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
				List<IVariable> off = crossover.crossover(selector.next().getVariable(), selector.next().getVariable(), problem, rand);
				// mutation
				for (IVariable offspring : off) {
					if (rand.nextDouble() < this.probMutation) {
						offspring = mutation.mutate(offspring, problem, rand);
					}
					offsprings.add(evaluator.evaluate(problem, offspring));
				}
			}
			population.addAll(offsprings);

			// eliminate duplicates to ensure variety in the population
			population = new SolutionSet(new HashSet<>(population));
			// truncate the population -> survival of the fittest
			sortBySingleObjective(population);
			population = new SolutionSet(population.subList(0, Math.min(population.size(), populationSize)));
			
			/*			
			for (Solution s : population.subList(0, Math.min(population.size(), 15))) {
				System.out.println(String.format("%s %s", s.getObjectives(0), s.hashCode()));
			}
			System.out.println("---------------------------");
			*/
			
		}
		
		return population.get(0);
	}
	
	
	public static void sortBySingleObjective(SolutionSet set) {
		set.sort(comp);
	}


	public SolutionSet getPopulation() {
		return population;
	}
	
	
	

}
