package com.msu.moo.operators.mutation;

import java.util.List;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.model.variable.ListVariable;
import com.msu.moo.util.MyRandom;

/**
 * Bitflip mutation of a boolean list. Always at least one bitflip is done! 
 *
 * @param <V>
 */
public class BitflipMutation<V extends ListVariable<Boolean>> implements IMutation<V> {

	// ! probability of a bitflip
	protected Double prob = null;
	
	
	public BitflipMutation() {
		super();
	}


	public BitflipMutation(Double prob) {
		super();
		this.prob = prob;
	}
	


	@Override
	public void mutate(V a, MyRandom rand) {
		
		List<Boolean> list = a.decode();
		
		boolean isMutated = false;
		if (prob == null) prob = 1 / (double) list.size();
		
		
		// iterate over all possible items to pick
		for (int i = 0; i < list.size(); i++) {
			
			// bit flip with a probability
			if (rand.nextDouble() < prob) {
				// bitflip
				list.set(i, !list.get(i));
				isMutated = true;
			}
			
		}
		
		// at least one bitflip
		if (!isMutated) {
			final int idx = rand.nextInt(list.size());
			list.set(idx, !list.get(idx));
		}
		
	}
	
	
	

	

	
	
}
