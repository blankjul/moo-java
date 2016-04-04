package com.msu.moo.experiment.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.msu.moo.algorithms.IAlgorithm;
import com.msu.moo.algorithms.impl.nsgaII.NSGAII;
import com.msu.moo.algorithms.impl.nsgaII.NSGAIIBinaryTournament;
import com.msu.moo.experiment.AExperiment;
import com.msu.moo.experiment.ExperimentCallback;
import com.msu.moo.interfaces.ICrossover;
import com.msu.moo.interfaces.IFactory;
import com.msu.moo.interfaces.IMutation;
import com.msu.moo.interfaces.ISolution;
import com.msu.moo.model.solution.NonDominatedSet;
import com.msu.moo.model.variable.BooleanListVariable;
import com.msu.moo.model.variable.ListVariable;
import com.msu.moo.operators.crossover.SinglePointCrossover;
import com.msu.moo.operators.factory.RandomListVariableFactory;
import com.msu.moo.problems.ZDT.ZDT5;
import com.msu.moo.util.Builder;
import com.msu.moo.util.MyRandom;

public class ZDT5Experiment extends AExperiment<NonDominatedSet<ISolution<BooleanListVariable>>, BooleanListVariable, ZDT5> {


	
	
	@Override
	protected void setAlgorithms(ZDT5 problem,
			List<IAlgorithm<NonDominatedSet<ISolution<BooleanListVariable>>, BooleanListVariable, ZDT5>> algorithms) {
	

		Builder<NSGAII<BooleanListVariable, ZDT5>> builder = new Builder<>(NSGAII.class);
		builder
			.set("probMutation", 0.9)
			.set("populationSize", 100)
			.set("factory", new BooleanListFactory(Arrays.asList(30,5,5,5,5,5,5,5,5,5,5)))
			.set("crossover", new ListBooleanCrossover())
			.set("mutation", new BooleanListListBitflip())
		    .set("selector", new NSGAIIBinaryTournament<>());
		
		algorithms.add(builder.build());
		
	}
	

	
	@Override
	protected void setProblems(List<ZDT5> problems) {
		problems.add(new ZDT5());
	}
	
	

	@Override
	protected void analyse(ExperimentCallback<NonDominatedSet<ISolution<BooleanListVariable>>, BooleanListVariable, ZDT5> c) {
	}
	
	
	
	private class BooleanListFactory implements IFactory<BooleanListVariable>{

		List<Integer> numberOfValues;
		
		
		public BooleanListFactory(List<Integer> numberOfValues) {
			super();
			this.numberOfValues = numberOfValues;
		}


		@Override
		public BooleanListVariable next(MyRandom rand) {
			List<List<Boolean>> list = new ArrayList<>(numberOfValues.size());
			
			for(int num : numberOfValues) {
				RandomListVariableFactory<Boolean> fac = new RandomListVariableFactory<>(Arrays.asList(true,false), num);
				list.add(fac.next(rand).decode());
			}
			return new BooleanListVariable(list);
		}

	}

	
	private class ListBooleanCrossover implements ICrossover<ListVariable<List<Boolean>>> {

		@Override
		public List<ListVariable<List<Boolean>>> crossover(ListVariable<List<Boolean>> a, ListVariable<List<Boolean>> b, MyRandom rand) {

			List<List<Boolean>> off1 = new ArrayList<>();
			List<List<Boolean>> off2 = new ArrayList<>();

			final int n = a.decode().size();

			for (int i = 0; i < n; i++) {

				List<Boolean> parent1 = a.decode().get(i);
				List<Boolean> parent2 = b.decode().get(i);
				
				if (rand.nextDouble() < n) {
					List<List<Boolean>> children = new SinglePointCrossover<Boolean>().crossover(parent1, parent2, rand);
					off1.add(children.get(0));
					off2.add(children.get(1));
				} else {
					off1.add(new ArrayList<>(parent1));
					off2.add(new ArrayList<>(parent2));
				}
			}

			return Arrays.asList(new BooleanListVariable(off1), new BooleanListVariable(off2));
		}

	}
	
	
	
	private class BooleanListListBitflip implements IMutation<ListVariable<List<Boolean>>>{

		@Override
		public void mutate(ListVariable<List<Boolean>> var, MyRandom rand) {
			
			for(List<Boolean> b : var.decode()) {
				final double prob = 0.0125;
				for (int i = 0; i < b.size(); i++) {
					if (rand.nextDouble() < prob) {
						b.set(i, !b.get(i));
					}
				}
			}
			
		}

	}





}
