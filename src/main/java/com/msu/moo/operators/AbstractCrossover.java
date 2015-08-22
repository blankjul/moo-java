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

	public List<IVariable> crossover(IVariable a, IVariable b) {

		// TODO: This solution is very ugly. could be improved but all crossover
		// subclasses has to be changed.
		try {
			
			@SuppressWarnings("unchecked")
			T first = (T) a.get();
			@SuppressWarnings("unchecked")
			T second = (T) b.get();
			List<T> offspring = crossover_(first,second);
			
			List<IVariable> result = new ArrayList<>();
			for (T o : offspring) {
				IVariable prototype = a.copy();
				prototype.set(o);
				result.add(prototype);
			}

			return result;
			
		} catch (Exception e){
			throw new RuntimeException("Crossover could not be performed. Wrong IVariable types!");
		}

	}

	public <V extends IVariable, P extends IProblem<V, P>> List<Solution> crossover(Evaluator<V, P> eval, Solution a,
			Solution b) {

		List<IVariable> vars = crossover(a.getVariable(), b.getVariable());
		List<Solution> result = new ArrayList<>();

		for (IVariable var : vars) {
			Solution s = eval.run(var);
			result.add(s);
		}

		return result;
	}
	

	abstract protected List<T> crossover_(T a, T b);

}
