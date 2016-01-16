package com.msu.operators.mutation;

import com.msu.interfaces.IMutation;
import com.msu.interfaces.IVariable;
import com.msu.util.MyRandom;

public class NoMutation implements IMutation<IVariable> {


	@Override
	public void mutate(IVariable a, MyRandom rand) {
	}

}
