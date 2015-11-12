package com.msu.operators.selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.util.Random;
import com.msu.operators.AbstractSelection;


/**
 * This is a binary tournament select which could be used for sending
 * always to individuals to a tournament and choose the winner by using a comparator!	
 *
 */
public class BinaryTournamentSelection extends AbstractSelection {

	//! Comparator for choosing which solution is better.
	protected Comparator<Solution> cmp;
	
	//! current pool which is used for the selection
	protected Queue<Solution> q = null;
	
	/**
	 * Construct a binary tournament selector
	 * @param set which should be used for selection
	 * @param cmp comparator which defines the winner of the tournament
	 */
	public BinaryTournamentSelection(SolutionSet set, Comparator<Solution> cmp, Random rand) {
		super(set, rand);
		if (set.size() < 2) {
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
	public Solution next() {
		
		// get two solutions. if pool is empty just create a new one!
		if (q == null || q.isEmpty()) rndPool();
		Solution a = q.poll();
		if (q == null || q.isEmpty()) rndPool();
		Solution b = q.poll();
		
		// tournament of this two solution
		Solution winner = cmp.compare(a, b) == 1 ? a : b;
		
		return winner;
	}
	
	protected void rndPool() {
		LinkedList<Solution> tmp = new LinkedList<>(set);
		rand.shuffle(tmp);
		q = new LinkedList<>(tmp);
	}

}
