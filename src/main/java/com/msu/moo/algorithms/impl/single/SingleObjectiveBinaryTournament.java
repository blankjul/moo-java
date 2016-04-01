package com.msu.moo.algorithms.impl.single;

import java.util.Comparator;
import java.util.List;

import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.SingleObjectiveComparator;
import com.msu.moo.model.solution.Solution;
import com.msu.moo.operators.selection.BinaryTournamentSelection;
import com.msu.moo.util.MyRandom;

public class SingleObjectiveBinaryTournament<S extends Solution<V>,V extends IVariable> extends BinaryTournamentSelection<S,V> {

	public SingleObjectiveBinaryTournament(List<S> set, MyRandom rand) {
		super(null);

		this.cmp = new Comparator<S>() {
			@Override
			public int compare(S o1, S o2) {
				return -1 * new SingleObjectiveComparator().compare(o1, o2);
			}
		};

	}

}
