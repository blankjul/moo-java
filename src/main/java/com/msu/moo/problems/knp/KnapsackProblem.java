package com.msu.moo.problems.knp;

import java.util.ArrayList;

import com.msu.moo.model.AbstractEvaluator;
import com.msu.moo.model.interfaces.IProblem;

/**
 * This class represents the knapsack problem.
 * 
 * The Problem is defined by items that could be added and a maximal weight that
 * fits into the knapsack.
 *
 */
public class KnapsackProblem implements IProblem<KnapsackVariable, KnapsackProblem> {

	// ! maximal weight of the knapsack
	protected int maxWeight;

	// ! all items that could be added to the knapsack
	protected ArrayList<Item> items;

	/**
	 * Create a Knapsack Problem with predefined items and a maximal weight!
	 * 
	 * @param maxWeight
	 *            maximal weight of the knapsack
	 * @param items
	 *            that could be added
	 */
	public KnapsackProblem(int maxWeight, ArrayList<Item> items) {
		super();
		this.maxWeight = maxWeight;
		this.items = items;
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public ArrayList<Item> getItems() {
		return items;
	}


	@Override
	public AbstractEvaluator<KnapsackVariable, KnapsackProblem> getEvaluator() {
		return new KnapsackEvaluator(this);
	}



	


}
