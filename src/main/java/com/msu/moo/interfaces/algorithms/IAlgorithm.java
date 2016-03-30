package com.msu.moo.interfaces.algorithms;

import com.msu.moo.interfaces.IEvaluator;
import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.MyRandom;

public interface IAlgorithm<R, V extends IVariable, P extends IProblem<V>> {

	public R run(P problem, IEvaluator evaluator, MyRandom rand);

}

