package com.msu.operators.mutation;


import java.util.ArrayList;
import java.util.List;

import com.msu.interfaces.IProblem;
import com.msu.operators.AbstractMutation;
import com.msu.util.MyRandom;
import com.msu.util.Util;

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
	protected List<T> mutate_(List<T> var, IProblem problem,  MyRandom rand) {
		List<T> obj = new ArrayList<>(var);
		final double prob = 1 / (double) obj.size();
		
		for (int i = 0; i < obj.size(); i++) {
			double v = rand.nextDouble();
			if (v < prob) {
				int point = rand.nextInt(0, obj.size()-1);
				Util.swap(obj, i, point);
			}
		}
		return obj;
	}
	

}
