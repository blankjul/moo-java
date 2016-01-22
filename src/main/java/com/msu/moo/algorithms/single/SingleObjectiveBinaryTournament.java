package com.msu.moo.algorithms.single;

import java.util.Comparator;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.SingleObjectiveComparator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.model.solution.SolutionSet;
import com.msu.moo.operators.selection.BinaryTournamentSelection;
import com.msu.moo.util.MyRandom;

public class SingleObjectiveBinaryTournament<V extends IVariable> extends BinaryTournamentSelection<V> {

	public SingleObjectiveBinaryTournament(SolutionSet<V> set, MyRandom rand) {
		super(set, null, rand);

		this.cmp = new Comparator<Solution<V>>() {
			@Override
			public int compare(Solution<V> o1, Solution<V> o2) {
				return -1 * new SingleObjectiveComparator().compare(o1, o2);
			}
		};

	}

}
