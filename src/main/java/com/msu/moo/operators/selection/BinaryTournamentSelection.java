package com.msu.moo.operators.selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import com.msu.moo.model.solution.MultiObjectiveSolution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.AbstractSelection;
import com.msu.moo.util.Random;


/**
 * This is a binary tournament select which could be used for sending
 * always to individuals to a tournament and choose the winner by using a comparator!	
 *
 */
public class BinaryTournamentSelection extends AbstractSelection {

	//! Comparator for choosing which solution is better.
	protected Comparator<MultiObjectiveSolution> cmp;
	
	//! current pool which is used for the selection
	protected Queue<MultiObjectiveSolution> q = null;
	
	/**
	 * Construct a binary tournament selector
	 * @param set which should be used for selection
	 * @param cmp comparator which defines the winner of the tournament
	 */
	public BinaryTournamentSelection(SolutionSet set, Comparator<MultiObjectiveSolution> cmp) {
		super(set);
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
	public MultiObjectiveSolution next() {
		
		// get two solutions. if pool is empty just create a new one!
		if (q == null || q.isEmpty()) rndPool();
		MultiObjectiveSolution a = q.poll();
		if (q == null || q.isEmpty()) rndPool();
		MultiObjectiveSolution b = q.poll();
		
		// tournament of this two solution
		MultiObjectiveSolution winner = cmp.compare(a, b) == 1 ? a : b;
		
		return winner;
	}
	
	protected void rndPool() {
		LinkedList<MultiObjectiveSolution> tmp = new LinkedList<>(set);
		Random.getInstance().shuffle(tmp);
		q = new LinkedList<>(tmp);
	}

}
