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
	
	
	public Range() {
		super();
	}

	public Range(int n, T min, T max) {
		ranges = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ranges.add(Pair.create(min, max));
		}
	}

	public boolean add(List<T> l) {
		
		boolean change = false;
		
		// if we have no boundaries yet -> initialize
		if (ranges == null) {
			ranges = new ArrayList<>();
			for (int i = 0; i < l.size(); i++) {
				ranges.add(Pair.create(l.get(i), l.get(i)));
			}
			change = true;
		// update all the points
		} else {
			for (int i = 0; i < l.size(); i++) {
				if (l.get(i).compareTo(ranges.get(i).first) == -1) {
					ranges.get(i).first = l.get(i);
					change = true;
				}
				if (l.get(i).compareTo(ranges.get(i).second) == 1) {
					ranges.get(i).second = l.get(i);
					change = true;
				}
			}
		}
		return change;
	}
	
	public List<Pair<T,T>> get() {
		return ranges;
	}
	
	public T getMinimum(int n) {
		return ranges.get(n).first;
	}
	
	public T getMaximum(int n) {
		return ranges.get(n).second;
	}
	
	public int size() {
		return ranges.size();
	}
	
	public Range<T> setMinimum(int n, T value) {
		ranges.get(n).first = value;
		return this;
	}
	
	public Range<T> setMaximum(int n, T value) {
		ranges.get(n).second = value;
		return this;
	}
	
	public Range<T> set(int n, T min, T max) {
		setMinimum(n, min);
		setMaximum(n, max);
		return this;
	}
	
	
	
}
