package com.msu.moo.operators.mutation;

import com.msu.moo.interfaces.IMutation;
import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.moo.util.MyRandom;
import com.msu.moo.util.Range;

public class SimulatedBinaryMutation implements IMutation<DoubleListVariable> {

	// ! range of the results
	protected Range<Double> range = null;

	// ! distribution index
	protected double eta_m = 20.0;

	protected double mProbability = 0.033;

	public SimulatedBinaryMutation(Range<Double> range) {
		super();
		this.range = range;
	}

	@Override
	public void mutate(DoubleListVariable a, MyRandom rand) {

		double rnd, delta1, delta2, mut_pow, deltaq;
		double y, yl, yu, val, xy;

		for (int var = 0; var < a.size(); var++) {
			if (rand.nextDouble() <= mProbability) {
				
				y = a.get(var);
				yl = range.getMinimum(var);
				yu = range.getMaximum(var);

				delta1 = (y - yl) / (yu - yl);
				delta2 = (yu - y) / (yu - yl);
				rnd = rand.nextDouble();
				mut_pow = 1.0 / (eta_m + 1.0);
				if (rnd <= 0.5) {
					xy = 1.0 - delta1;
					val = 2.0 * rnd + (1.0 - 2.0 * rnd) * (Math.pow(xy, (eta_m + 1.0)));
					deltaq = Math.pow(val, mut_pow) - 1.0;
				} else {
					xy = 1.0 - delta2;
					val = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5) * (Math.pow(xy, (eta_m + 1.0)));
					deltaq = 1.0 - (Math.pow(val, mut_pow));
				}
				y = y + deltaq * (yu - yl);
				if (y < yl)
					y = yl;
				if (y > yu)
					y = yu;
				
				a.set(var, y);
			}
		}

	}

}
