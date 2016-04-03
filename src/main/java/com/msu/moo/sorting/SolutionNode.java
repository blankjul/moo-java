package com.msu.moo.sorting;

/**
 * This is a node which has information about the solution accessed by index.
 */
public class SolutionNode {
	
	/**
	 * Index to the corresponding solution in the population
	 */
	protected int index;
	
	/**
	 * Is dominating further solutions in that objective
	 */
	protected boolean[] isDominatingInObjective = null;

	/**
	 * True whenever this node was ranked once
	 */
	protected Integer rank = null;

	
	/**
	 * Create an empty node with the corresponding solution index
	 * @param index
	 */
	public SolutionNode(int index, int m) {
		super();
		this.index = index;
		isDominatingInObjective = new boolean[m];
	}


	@Override
	public String toString() {
		return "MyNode [index=" + index + ", isDominating=" + isDominatingInObjective + ", rank=" + rank + "]";
	}


	public boolean isRanked() {
		return rank != null;
	}


	public void setRank(Integer rank) {
		this.rank = rank;
	}
	

	
}
