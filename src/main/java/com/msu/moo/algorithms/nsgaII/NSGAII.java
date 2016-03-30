package com.msu.moo.algorithms.nsgaII;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.msu.moo.algorithms.AMultiObjectiveEvolutionaryAlgorithm;
import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.GenericSolutionSet;
import com.msu.moo.model.solution.NonDominatedSolutionSet;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.selection.BinaryTournamentSelection;
import com.msu.moo.util.IListener;
import com.msu.moo.util.MyRandom;

/**
 * This algorithm is implemented in the base of NSGAII proposed by Professor Deb
 * in "A fast and elitist multiobjective genetic algorithm: NSGA-II".
 *
 */
public class NSGAII<V extends IVariable, P extends IProblem<V>> extends AMultiObjectiveEvolutionaryAlgorithm<V,P> {


	// ! allow external listener to get updates
	protected IListener<GenericSolutionSet<Solution<V>,V>> listener;
	
	// population with NSGAII solutions -> allows to save rank and crowding directly
	protected NSGAIISolutionSet<V> population = null;
	
	// recommended for discrete problems
	protected boolean elimanateDuplicates = false;
	
	// ! private constructor! use the builder!
	protected NSGAII() {
	}
	

	@Override
	public NonDominatedSolutionSet<V> run_(P problem, IEvaluator evaluator, MyRandom rand)  {

		// initialize the population and calculate also rank and crowding
		initializePopulation(problem, evaluator, rand);
		if (listener != null) listener.notify(new SolutionSet<>(population));
		
		while (evaluator.hasNext()) {
			
			// binary tournament selection for mating
			RankAndCrowdingComparator<V> comp = new RankAndCrowdingComparator<>();
			BinaryTournamentSelection<NSGAIISolution<V>, V> bts = new BinaryTournamentSelection<NSGAIISolution<V>, V>(population,comp, rand);

			// create offspring population until size two times
			NSGAIISolutionSet<V> offsprings = new NSGAIISolutionSet<V>(populationSize);
			while (offsprings.size() < populationSize) {
				
				// crossover
				Solution<V> p1 = bts.next();
				Solution<V> p2 = bts.next();
				
				List<V> off = crossover.crossover(p1.getVariable(), p2.getVariable(), rand);
				
				
				for (V offspring : off) {
					
					// mutation of the offspring
					if (rand.nextDouble() < this.probMutation) {
						mutation.mutate(offspring, rand);
					}
					
					// evaluate 
					Solution<V> s = evaluator.evaluate(problem, offspring);
					
					offsprings.add(new NSGAIISolution<>(s));
				}
			}

			// merge population and offsprings
			population.addAll(offsprings);

			// eliminate duplicates to ensure variety in the population
			if (elimanateDuplicates) population = new NSGAIISolutionSet<V>(new HashSet<>(population));
			
			// survival of the best population
			population = calcRankAndCrowding(population);
			
			// truncate the population
			population = new NSGAIISolutionSet<V>(population.subList(0, Math.min(populationSize, population.size())));

			// update notifier and evaluator
			if (listener != null) listener.notify(new SolutionSet<V>(population));
			evaluator.notify(new SolutionSet<V>(population));
			
		}
		
		//System.out.println(evaluator.numOfEvaluations());
		//System.out.println(Hypervolume.calculate(population,Arrays.asList(1.01, 1.01)));
		
		return new NonDominatedSolutionSet<V>(population);

	}

	protected void initializePopulation(P problem, IEvaluator evaluator, MyRandom rand) {
	
		// initialize the population with populationSize
		population = new NSGAIISolutionSet<V>(populationSize * 2);

		for (int i = 0; i < populationSize; i++) {
			Solution<V> s = evaluator.evaluate(problem, factory.next(rand));
			population.add(new NSGAIISolution<>(s));
		}

		// calculate Rank and Crowding for the initial population
		// it is necessary for the BinaryTournamentSelection
		calcRankAndCrowding(population);

	}

	
	
	protected NSGAIISolutionSet<V> calcRankAndCrowding(NSGAIISolutionSet<V> population) {
		
		NSGAIISolutionSet<V> solutions = new NSGAIISolutionSet<V>();
		
		int ranking = 0;
		
		// for every front of the population
		for (NonDominatedSolutionSet<V> front : NaiveNonDominatedSorting.sort(population)) {
			
			NSGAIISolutionSet<V> next = new NSGAIISolutionSet<V>();
			front.getSolutions().forEach(s -> next.add((NSGAIISolution<V>) s));
			
			// calculate crowding
			CrowdingIndicator.calculate(next);
			
			// sort by crowding
			Collections.sort(next, (o1, o2)-> Double.compare(o2.getCrowding(), o1.getCrowding()));
			
			// add solution to new population and save ranking
			for (NSGAIISolution<V> s : next) {
				solutions.add(s);
				s.setRank(ranking);
			}
			
			// since all other individuals are truncated anyway we could stop here
			if (solutions.size() > populationSize) break;
			
			ranking++;
			
		}
		
		//Comparator<NSGAIISolution<V>> comp = Comparator.comparingInt(NSGAIISolution::getRank);
		//comp.thenComparingDouble(NSGAIISolution::getCrowding).;

		return solutions;
		
	}


	


}
