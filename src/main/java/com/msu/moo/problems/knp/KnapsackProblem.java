package com.msu.moo.problems.knp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.msu.moo.model.AbstractProblem;

/**
 * This class represents the knapsack problem.
 * 
 * The Problem is defined by items that could be added and a maximal weight that
 * fits into the knapsack.
 *
 */
public class KnapsackProblem extends AbstractProblem<KnapsackVariable> {

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
	protected List<Double> evaluate_(KnapsackVariable variable) {
		List<Boolean> b = variable.get();
		List<Item> items = getItems();
		if (b.size() != items.size())
			throw new RuntimeException("Sizes of the varialbes are different " + b.size() + " != " + items.size());
		int weight = getWeight(items, b);
		if (weight > getMaxWeight())
			return new ArrayList<Double>(Arrays.asList(0d));
		else
			return new ArrayList<Double>(Arrays.asList((double) getProfit(items, b)));
	}


	public static <T extends Item> Integer getWeight(List<T> items, List<Boolean> b) {
		return getSumItemAttribute(items, b, i -> i.getWeight());
	}

	public static <T extends Item> Integer getProfit(List<T> items, List<Boolean> b) {
		return getSumItemAttribute(items, b, i -> i.getProfit());
	}

	/**
	 * Calculate the sum by using a lambda expression
	 * 
	 * @param items
	 *            that could be added
	 * @param b
	 *            for the knapsack
	 * @param func
	 *            lambda expression
	 * @return resulting weight
	 */
	public static <T extends Item> Integer getSumItemAttribute(List<T> items, List<Boolean> b,
			Function<T, Integer> func) {
		int weight = 0;
		for (int j = 0; j < b.size(); j++) {
			if (b.get(j))
				weight += func.apply(items.get(j));
		}
		return weight;
	}

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}




	


}
