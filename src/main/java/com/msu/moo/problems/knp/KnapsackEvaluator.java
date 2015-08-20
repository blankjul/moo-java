package com.msu.moo.problems.knp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.msu.moo.model.Evaluator;

public class KnapsackEvaluator extends Evaluator<KnapsackVariable, KnapsackProblem> {

	@Override
	protected <T> List<Double> evaluate(KnapsackProblem problem, KnapsackVariable variable) {
		List<Boolean> b = variable.plan;
		List<Item> items = problem.getItems();
		if (b.size() != items.size())
			throw new RuntimeException("Sizes of the varialbes are different " + b.size() + " != " + items.size());
		int weight = getWeight(items, b);
		if (weight > problem.getMaxWeight())
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

}
