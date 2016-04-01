package com.msu.moo.interfaces;

import com.msu.moo.algorithms.IAlgorithm;

/**
 * Callback class which is needed in order to get results from the experiments.
 * When threads are finished with executing for every run the callback method is
 * called.
 *
 * @param <R> Result type: Solution<V> or NonDominatedSolutionSet<V>
 * @param <V> Variable type
 * @param <P> Problem type
 */
public interface ICallback<R, V extends IVariable, P extends IProblem<V>> {

	public void analyze(P problem, IAlgorithm<R, V, P> algorithm, int run, R result);

}