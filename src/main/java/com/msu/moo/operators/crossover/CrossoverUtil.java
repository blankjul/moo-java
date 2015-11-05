package com.msu.moo.operators.crossover;

import org.apache.commons.math3.util.FastMath;

import com.msu.moo.util.Pair;
import com.msu.moo.util.Random;

public class CrossoverUtil {
	
	/**
	 * Return always a section from a allele, which means a lower and a upper bound.
	 * @param length maximal length
	 * @return pair of integer. lb and ub.
	 */
	public static Pair<Integer, Integer> getSection(int length, Random rand) {
        // choose random points, making sure that lb < ub.
        int rnd1 = rand.nextInt(length);
        int rnd2;
        do {
            rnd2 = rand.nextInt(length);
        } while (rnd1 == rnd2);
        
        // determine the lower and upper bounds
        final int lb = FastMath.min(rnd1, rnd2);
        final int ub = FastMath.max(rnd1, rnd2);
        
        return new Pair<Integer, Integer>(lb, ub);

	}

}
