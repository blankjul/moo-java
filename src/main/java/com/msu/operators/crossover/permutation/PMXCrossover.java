package com.msu.operators.crossover.permutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.msu.operators.AbstractListCrossover;
import com.msu.operators.crossover.CrossoverUtil;
import com.msu.util.Pair;


public class PMXCrossover<T> extends AbstractListCrossover<T> {

	
	protected List<T> crossover_(List<T> a, List<T> b, int lb, int ub) {
		
		// create structures for the child
		final int length = a.size();
		final List<T> child = new ArrayList<T>(a);
		final Map<T, T> map = new HashMap<T, T>(length);
		
		// create the partial mapping
		for (int i = lb; i <= ub; i++) map.put(a.get(i), b.get(i));
		
		for (int i = 0; i < length; i++) {
        	if (i >= lb && i <= ub) continue;
        	child.set(i, null);
		}
		
	     // iterate over every item from the parent starting right from ub
        for (int i = ub + 1; i < length + lb; i++) {
        	
        	// resolve the partial mapping
        	final int idx = i % length;
        	T item = b.get(idx);    
        	
        	while (map.containsKey(item)) {
        		item = map.get(item);
        	} 
        	child.set(idx, item);
        }
        
		return child;
	}
	
	
	@Override
	protected List<List<T>> crossoverLists(List<T> a, List<T> b) {
		
        Pair<Integer, Integer> bounds = CrossoverUtil.getSection(a.size(), rand);

		return new ArrayList<>( Arrays.asList(crossover_(a, b, bounds.first, bounds.second), 
				crossover_(b, a, bounds.first, bounds.second)));

	}

}
