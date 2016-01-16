package com.msu.operators.selection;

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
public class RandomSelection<T> extends ASelection<T> {

	//! current pool which is used for the selection
	protected Queue<Solution<T>> q = null;
	
	/**
	 * Construct a binary tournament selector
	 * @param set which should be used for selection
	 */
	public RandomSelection(SolutionSet<T> set, MyRandom rand) {
		super(set, rand);
	}
	
	@Override
	public Solution<T> next() {
		return rand.select(set);
	}
	


}
