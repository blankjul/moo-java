package com.msu.interfaces;

import com.msu.util.MyRandom;

/**
 * This interface provides a mutation of the variable decoding. The variable is
 * directly change on the original variable and no copy is made.
 *
 * @param <D>
 *            decoded type of the variable
 */
public interface IMutation<V extends IVariable, P extends IProblem<V>> {

	public void mutate(P problem, MyRandom rand, V a);

}