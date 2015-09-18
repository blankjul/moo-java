package com.msu.moo.fonseca;

import java.util.List;

import com.msu.moo.model.solution.MultiObjectiveSolution;
import com.msu.moo.model.solution.SolutionSet;

public class FonsecaUtil {

	public static String toString(List<Double> l) {
		StringBuilder line = new StringBuilder();
		for (Double d : l) {
			line.append(d);
			line.append(" ");
		}
		return line.toString();
	}
	
	
	public static String toString(SolutionSet set) {
		StringBuilder sb = new StringBuilder();
		for (MultiObjectiveSolution s : set) {
			sb.append(FonsecaUtil.toString(s.getObjective()));
			sb.append("\n");
		}
		return sb.toString();
	}

	
}
