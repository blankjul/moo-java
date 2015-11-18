package com.msu.operators.selection;

import java.util.Queue;

import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.operators.AbstractSelection;
import com.msu.util.Random;


/**
 * This is a binary tournament select which could be used for sending
 * always to individuals to a tournament and choose the winner by using a comparator!	
 *
 */
public class RandomSelection extends AbstractSelection {

	//! current pool which is used for the selection
	protected Queue<Solution> q = null;
	
	/**
	 * Construct a binary tournament selector
	 * @param set which should be used for selection
	 */
	public RandomSelection(SolutionSet set, Random rand) {
		super(set, rand);
	}
	
	@Override
	public Solution next() {
		return rand.select(set);
	}
	


}
