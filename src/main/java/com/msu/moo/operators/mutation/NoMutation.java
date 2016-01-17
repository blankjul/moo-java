package com.msu.moo.operators.mutation;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.interfaces.IVariable;
import com.msu.moo.util.MyRandom;

public class NoMutation implements IMutation<IVariable> {


	@Override
	public void mutate(IVariable a, MyRandom rand) {
	}

}
