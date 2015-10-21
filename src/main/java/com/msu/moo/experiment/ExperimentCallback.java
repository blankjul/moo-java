package com.msu.moo.experiment;

import com.msu.moo.model.solution.NonDominatedSolutionSet;

public class ExperimentCallback {
	
	public int i;
	public int j;
	public int k;
	public long duration;
	public NonDominatedSolutionSet set;
	
	public ExperimentCallback(int i, int j, int k) {
		super();
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	

}
