package com.msu.moo.operators;

import java.util.ArrayList;
import java.util.List;

import com.msu.moo.model.Evaluator;
import com.msu.moo.model.interfaces.IProblem;
import com.msu.moo.model.interfaces.IVariable;
import com.msu.moo.model.solution.Solution;

/**
 * This is an abstract Crossover of an object. This class which inherits from
 * this one could specify which type is expected to be used to crossover.
 * Otherwise there will be an error thrown.
 *
 * @param <T>
 *            type which could be mutated!
 */
public abstract class AbstractCrossover<T> {

	public List<IVariable<T>> crossover(IVariable<T> a, IVariable<T> b) {

		// TODO: This solution is very ugly. could be improved but all crossover
		// subclasses has to be changed.

		List<T> genomes = crossover_(a.get(), b.get());
		List<IVariable<T>> result = new ArrayList<>();

		for (T g : genomes) {
			IVariable<T> tmp = a.copy();
			tmp.set(g);
			result.add(tmp);
		}

		return result;
	}

	public <V extends IVariable<?>, P extends IProblem<V, P>> List<Solution> crossover(Evaluator<V, P> eval, Solution a,
			Solution b) {

		List<IVariable<T>> vars = crossover(a.getVariable(), b.getVariable());
		List<Solution> result = new ArrayList<>();

		for (IVariable<T> var : vars) {
			Solution s = new Solution(var, eval.run(var));
		}

		return result;
	}

	abstract protected List<T> crossover_(T a, T b);

}
