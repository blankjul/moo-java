package com.msu.moo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows to save always the range of a collection of points.
 *
 * @param <T> type of the points.
 */
public class Range<T extends Comparable<T>> {
	
	protected List<Pair<T,T>> ranges = null;
	
	public void add(List<T> l) {
		
		// if we have no boundaries yet -> initialize
		if (ranges == null) {
			ranges = new ArrayList<>();
			for (int i = 0; i < l.size(); i++) {
				ranges.add(Pair.create(l.get(i), l.get(i)));
			}
		// update all the points
		} else {
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i).compareTo(ranges.get(i).first) == -1) ranges.get(i).first = l.get(i);
				if (l.get(i).compareTo(ranges.get(i).second) == 1) ranges.get(i).second = l.get(i);
			}
		}
	}
	
	public List<Pair<T,T>> get() {
		return ranges;
	}
	
}
