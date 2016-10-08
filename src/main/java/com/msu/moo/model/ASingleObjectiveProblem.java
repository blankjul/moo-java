package com.msu.moo.model;

import com.msu.moo.interfaces.IVariable;

/**
 * Abstract class which defines a single objective problem. Number of objectives
 * is always 1!
 * 
 * @param <V>
 *            Variable type
 */
public abstract class ASingleObjectiveProblem<V extends IVariable> extends AProblem<V> {

	@Override
	public int getNumberOfObjectives() {
		return 1;
	}

}
