package com.msu.operators.mutation;

import com.msu.interfaces.IMutation;
import com.msu.interfaces.IProblem;
import com.msu.interfaces.IVariable;
import com.msu.util.MyRandom;

public class NoMutation implements IMutation<IVariable, IProblem<IVariable>> {


	@Override
	public void mutate(IProblem<IVariable> problem, MyRandom rand, IVariable a) {
	}

}
