/* Copyright 2009-2015 David Hadka
 *
 * This file is part of the MOEA Framework.
 *
 * The MOEA Framework is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * The MOEA Framework is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the MOEA Framework.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.msu.moo.problems.ZDT;

import java.util.List;

import com.msu.moo.model.variable.DoubleListVariable;
import com.msu.util.Range;
import com.msu.util.exceptions.EvaluationException;

public class ZDT4 extends AbstractZDT {

	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {
		List<Double> v = var.decode();

		if (v.size() != 10) 
			throw new EvaluationException(String.format("Variable needs to have 10 entries, but %s are provided.", v.size()));
		
		
		double g = 0.0;
		for (int i = 1; i < v.size(); i++) {
			g += Math.pow(v.get(i), 2.0) - 10.0 * Math.cos(4.0 * Math.PI * v.get(i));
		}
		g += 1.0 + 10.0 * (v.size() - 1);

		double h = 1.0 - Math.sqrt(v.get(0) / g);

		objectives.add(v.get(0));
		objectives.add(g * h);

	}

	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(10, -5d, 5d).set(0, 0d, 1d);
	}

}
