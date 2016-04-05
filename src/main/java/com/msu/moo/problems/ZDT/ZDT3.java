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
import com.msu.moo.util.Range;


public class ZDT3 extends AbstractZDT {


	public ZDT3() {
		super(new Range<Double>(30, 0d, 1d));
	}



	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives, List<Double> constraintViolations) {

		List<Double> v = var.decode();
		double g = 0.0;
		for (int i = 1; i < v.size(); i++) {
			g += v.get(i);
		}
		g = (9.0 / (v.size() - 1)) * g + 1.0;

		double h = 1.0 - Math.sqrt(v.get(0) / g) - (v.get(0) / g)
				* Math.sin(10.0 * Math.PI * v.get(0));
		
		objectives.add(v.get(0));
		objectives.add(g * h);
		
	}





}
