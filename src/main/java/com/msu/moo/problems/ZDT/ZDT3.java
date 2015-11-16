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

import com.msu.model.variables.DoubleListVariable;
import com.msu.util.Range;


public class ZDT3 extends AbstractZDT {


	@Override
	protected void evaluate__(DoubleListVariable var, List<Double> objectives) {
		double g = 0.0;
		for (int i = 1; i < var.size(); i++) {
			g += var.get(i);
		}
		g = (9.0 / (var.size() - 1)) * g + 1.0;

		double h = 1.0 - Math.sqrt(var.get(0) / g) - (var.get(0) / g)
				* Math.sin(10.0 * Math.PI * var.get(0));
		
		objectives.add(var.get(0));
		objectives.add(g * h);
		
	}



	@Override
	public Range<Double> getVariableConstraints() {
		return new Range<Double>(30, 0d, 1d);
	}
	



}
