package com.msu.operators.moea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.moeaframework.core.operator.real.SBX;
import org.moeaframework.core.variable.RealVariable;

import com.msu.moo.util.Random;
import com.msu.operators.AbstractListCrossover;

public class MOEASimulatedBinaryCrossover extends AbstractListCrossover<Double> {

	// ! minimal difference for executing the simulated binary crossover
	protected double EPS = 1.0e-30;

	// boundaries for the values
	protected double[] range = new double[] { Double.MIN_VALUE, Double.MAX_VALUE };

	//! distribution index
	protected double eta_c = 20.0;
	
	//! crossover probability in the list
	protected double cProbability = 0.5;


	public MOEASimulatedBinaryCrossover(double[] range) {
		super();
		this.range = range;
	}


	@Override
	protected List<List<Double>> crossoverLists(List<Double> parent1, List<Double> parent2, Random rand) {

		List<Double> child1 = new ArrayList<Double>(parent1);
		List<Double> child2 = new ArrayList<Double>(parent2);

		// for each double value in the list
		for (int i = 0; i < parent1.size(); i++) {

			// perform crossover if sbxProbability is given
			if (rand.nextDouble() <= cProbability) {
				
				RealVariable var1 = new RealVariable(parent1.get(i), range[0], range[1]);
				RealVariable var2 = new RealVariable(parent1.get(i), range[0], range[1]);
				SBX.evolve(var1, var2, 20);

				// set the offspring randomly to the children vectors
				if (rand.nextDouble() <= 0.5) {
					child1.set(i, var1.getValue());
					child2.set(i, var2.getValue());
				} else {
					child1.set(i, var2.getValue());
					child2.set(i, var1.getValue());
				}
			}
		}
		return new ArrayList<>(Arrays.asList(child1, child2));
	}

}
