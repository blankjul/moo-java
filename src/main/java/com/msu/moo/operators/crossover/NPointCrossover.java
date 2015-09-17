package com.msu.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.operators.AbstractListCrossover;
import com.msu.moo.util.Random;

public class NPointCrossover<T> extends AbstractListCrossover<T> {

	protected int crossoverPoints = 2;

	
	public NPointCrossover(int crossoverPoints) {
		super();
		this.crossoverPoints = crossoverPoints;
	}


	@Override
	protected List<List<T>> crossoverLists(List<T> p1, List<T> p2) {
		
	      final int length = p1.size();
	        if (crossoverPoints >= length) {
	            throw new RuntimeException("crossoverPoints is larger than list itself.");
	        }


        // array representations of the parents
        // and of the children
        final ArrayList<T> child1Rep = new ArrayList<T>(p1.size());
        final ArrayList<T> child2Rep = new ArrayList<T>(p2.size());

        final Random random = Random.getInstance();

        ArrayList<T> c1 = child1Rep;
        ArrayList<T> c2 = child2Rep;

        int remainingPoints = crossoverPoints;
        int lastIndex = 0;
        for (int i = 0; i < crossoverPoints; i++, remainingPoints--) {
            // select the next crossover point at random
            final int crossoverIndex = 1 + lastIndex + random.nextInt(length - lastIndex - remainingPoints);

            // copy the current segment
            for (int j = lastIndex; j < crossoverIndex; j++) {
                c1.add(p1.get(j));
                c2.add(p2.get(j));
            }

            // swap the children for the next segment
            ArrayList<T> tmp = c1;
            c1 = c2;
            c2 = tmp;

            lastIndex = crossoverIndex;
        }

        // copy the last segment
        for (int j = lastIndex; j < length; j++) {
            c1.add(p1.get(j));
            c2.add(p2.get(j));
        }

        return new ArrayList<>( Arrays.asList(c1, c2));
	}

}