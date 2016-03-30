package com.msu.moo.experiment.callback;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.interfaces.algorithms.IAlgorithm;

public interface ICallback<R, V extends IVariable, P extends IProblem<V>> {

	public void analyze(P problem, IAlgorithm<R, V, P> algorithm, int run, R result);


}