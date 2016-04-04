package com.msu.moo.algorithms.impl.nsgaII;

import java.util.List;

import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.Solution;

/**
 * This is a solution which is used by NSGAII because the rank and the crowding
 * distance are saved automatically to this object.
 */
public class NSGAIISolution<V> extends Solution<V> {

	// rank according the current population
	private Integer rank = null;

	// crowding distance according the current front
	private Double crowding = null;

	
	public NSGAIISolution(ISolution<V> s) {
		super(s.getVariable(), s.getObjectives(), s.getConstraintViolations());
	}
	
	public NSGAIISolution(V variable, List<Double> objectives) {
		super(variable, objectives);
	}

	public NSGAIISolution(V variable, List<Double> objectives, List<Double> constraintViolations) {
		super(variable, objectives, constraintViolations);
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getCrowding() {
		return crowding;
	}

	public void setCrowding(Double crowding) {
		this.crowding = crowding;
	}

	@Override
	public String toString() {
		return "NSGAIISolution [rank=" + rank + ", crowding=" + crowding + ", objective=" + objective + ", constraintViolations=" + constraintViolations + "]";
	}
	
	

}
