package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public interface IMultiObjectiveAlgorithm<P extends IProblem> extends IAlgorithm<P, NonDominatedSolutionSet> {
}
