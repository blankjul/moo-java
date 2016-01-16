package com.msu.operators.selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.msu.moo.model.ASelection;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.util.MyRandom;


/**
 * This is a binary tournament select which could be used for sending
 * always to individuals to a tournament and choose the winner by using a comparator!	
 *
 */
public class BinaryTournamentSelection<V>  extends ASelection<V>  {

	//! Comparator for choosing which solution is better.
	protected Comparator<Solution<V>> cmp;
	
	//! current pool which is used for the selection
	protected Queue<Solution<V>> q = null;
	
	/**
	 * Construct a binary tournament selector
	 * @param set which should be used for selection
	 * @param cmp comparator which defines the winner of the tournament
	 */
	public BinaryTournamentSelection(SolutionSet<V> set, Comparator<Solution<V>> cmp, MyRandom rand) {
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
	public Solution<V> next() {
		
		// get two solutions. if pool is empty just create a new one!
		if (q == null || q.isEmpty()) rndPool();
		Solution<V> a = q.poll();
		if (q == null || q.isEmpty()) rndPool();
		Solution<V> b = q.poll();
		
		// tournament of this two solution
		Solution<V> winner = cmp.compare(a, b) == 1 ? a : b;
		
		return winner;
	}
	
	protected void rndPool() {
		LinkedList<Solution<V>> tmp = new LinkedList<>(set);
		rand.shuffle(tmp);
		q = new LinkedList<>(tmp);
	}

}
