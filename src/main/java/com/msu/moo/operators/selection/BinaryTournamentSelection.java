package com.msu.moo.operators.selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.msu.moo.model.ASelection;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.util.MyRandom;


/**
 * This is a binary tournament select which could be used for sending
 * always to individuals to a tournament and choose the winner by using a comparator!	
 *
 */
public class BinaryTournamentSelection<S extends Solution<V>, V>  extends ASelection<S, V>  {

	//! Comparator for choosing which solution is better.
	protected Comparator<S> cmp;
	
	//! current pool which is used for the selection
	protected Queue<S> q = null;
	
	/**
	 * Construct a binary tournament selector
	 * @param set which should be used for selection
	 * @param cmp comparator which defines the winner of the tournament
	 */
	public BinaryTournamentSelection(List<S> set, Comparator<S> cmp, MyRandom rand) {
		super(set, rand);
		if (set.size() < 2) {
			System.out.println(set);
			throw new RuntimeException("For the tournament selection the SolutionSet has to be larger than 2!");
		}
		this.cmp = cmp;
		
	}
	

	/**
	 * Create a binary tournament, choose the winner. If the pool is empty there
	 * is created a new one!
	 * @return winner of the tournament
	 */
	@Override
	public S next() {
		
		// get two solutions. if pool is empty just create a new one!
		if (q == null || q.isEmpty()) rndPool();
		S a = q.poll();
		if (q == null || q.isEmpty()) rndPool();
		S b = q.poll();
		
		// tournament of this two solution
		S winner = cmp.compare(a, b) == 1 ? a : b;
		
		return winner;
	}
	
	protected void rndPool() {
		LinkedList<S> tmp = new LinkedList<>(set);
		rand.shuffle(tmp);
		q = new LinkedList<>(tmp);
	}

}
