package com.msu.moo.operators.mutation;


import java.util.List;

import com.msu.moo.operators.AbstractMutation;
import com.msu.moo.util.Random;
import com.msu.moo.util.Util;

/**
 * This is a SwapMutation which allows to define the range of the mutation. It
 * is only allowed on lists otherwise there will be an error thrown.
 * 
 * 	[0,1,2,3,4] -> swap index 1 and 3 -> [0,3,2,1,4]
 * 
 * The limitation of the swap mutation range could be useful the fix some values.
 * For example an array should only be changed in the middle and not in the beginning.
 *
 * @param <T> Type which the List is going to have
 */
public class SwapMutation<T> extends AbstractMutation<List<T>>{


	@Override
	protected void mutate_(List<T> obj, Random rand) {
		
		final double prob = 1 / obj.size();
		
		for (int i = 0; i < obj.size(); i++) {
			if (rand.nextDouble() < prob) {
				int point = rand.nextInt(0, obj.size()-1);
				Util.swap(obj, i, point);
			}
		}
		
	}
	

}
