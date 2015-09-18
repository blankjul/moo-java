package com.msu.moo.algorithms;

import com.msu.moo.interfaces.IProblem;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.model.solution.NonDominatedSolutionSet;

public abstract class AMultiObjectiveAlgorithm<V extends IVariable, P extends IProblem> extends AbstractAlgorithm<V, P, NonDominatedSolutionSet>{
}
