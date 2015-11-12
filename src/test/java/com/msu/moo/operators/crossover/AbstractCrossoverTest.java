package com.msu.moo.operators.crossover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.msu.moo.mocks.MockVariable;
import com.msu.moo.util.Random;
import com.msu.operators.AbstractCrossover;
import com.msu.operators.crossover.HalfUniformCrossover;
import com.msu.operators.crossover.SinglePointCrossover;
import com.msu.operators.crossover.UniformCrossover;
import com.msu.operators.crossover.permutation.CycleCrossover;
import com.msu.operators.crossover.permutation.EdgeRecombinationCrossover;
import com.msu.operators.crossover.permutation.OrderedCrossover;
import com.msu.operators.crossover.permutation.PMXCrossover;

@RunWith(Parameterized.class)
public class AbstractCrossoverTest {

	public final static List<AbstractCrossover<List<Integer>>> crossovers = 
			new ArrayList<>(Arrays.asList(
					new SinglePointCrossover<>(),new HalfUniformCrossover<Integer>(), new UniformCrossover<Integer>(),
					new CycleCrossover<>(), new EdgeRecombinationCrossover<>(), new OrderedCrossover<>(), new PMXCrossover<>()
					));

	public final static List<ArrayList<Integer>> inputs = new ArrayList<>(Arrays.asList(new ArrayList<Integer>(), new ArrayList<Integer>(1),
			new ArrayList<Integer>(Arrays.asList(1,2)), new ArrayList<Integer>(Arrays.asList(1,2,3))));

	@Parameters
	public static Collection<Object[]> data() {
		List<Object[]> data = new ArrayList<>();
		for (AbstractCrossover<List<Integer>> c : crossovers) {
			for (ArrayList<Integer> input : inputs) {
				data.add(new Object[] { c, new ArrayList<>(input), new ArrayList<>(input) });
			}
		}
		return data;
	}

	protected List<Integer> a;

	protected List<Integer> b;

	protected AbstractCrossover<List<Integer>> c;

	public AbstractCrossoverTest(AbstractCrossover<List<Integer>> c, List<Integer> a, List<Integer> b) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
	}

	@Test
	public void test() {
		c.crossover(new MockVariable(a), new MockVariable(b), new Random());
	}

}
