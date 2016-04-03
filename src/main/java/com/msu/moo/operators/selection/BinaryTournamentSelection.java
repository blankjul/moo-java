package com.msu.moo.operators.selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.ASelection;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.MyRandom;


/**
 * This is a binary tournament select which could be used for sending
 * always to individuals to a tournament and choose the winner by using a comparator!	
 *
 */
public class BinaryTournamentSelection<S extends ISolution<?>> extends ASelection<S>  {


	//! Comparator for choosing which solution is better.
	protected Comparator<S> cmp;
	
	
	public BinaryTournamentSelection(Comparator<S> cmp) {
		super();
		this.cmp = cmp;
	}


	protected void rndPool(List<S> population, MyRandom rand, Queue<S> q) {
		LinkedList<S> tmp = new LinkedList<>(population);
		rand.shuffle(tmp);
		q.addAll(tmp);
	}
	
	
	@Override
	public SolutionSet<S> next(List<S> population, int n, MyRandom rand) {
		
		if (population.size() < 2) {
			System.out.println(population);
			throw new RuntimeException("For the tournament selection the SolutionSet has to be larger than 2!");
		}
		
		
		SolutionSet<S> result = new SolutionSet<>();
		Queue<S> q = new LinkedList<>();
		
		
		while (result.size() < n) {
			
			
			// get two solutions. if pool is empty just create a new one!
			if (q.isEmpty()) rndPool(population, rand, q);
			S a = q.poll();
			if (q.isEmpty()) rndPool(population, rand, q);
			S b = q.poll();
			
			// tournament of this two solution
			S winner = cmp.compare(a, b) == 1 ? a : b;
			
			result.add(winner);
			
		}
		
		return result;
	}
	


}
