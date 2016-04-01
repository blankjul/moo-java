package com.msu.moo.interfaces;

import com.msu.moo.util.MyRandom;

/**
 * This interface provides a mutation of the variable decoding. The variable is
 * directly changed on the original variable and no copy is made.
 *
 * @param <V>
 *            variable that is mutated
 */
public interface IMutation<V extends IVariable> {

	public void mutate(V a, MyRandom rand);

}
