package com.msu.moo.fonseca;

import java.util.Collection;
import java.util.List;

import com.msu.moo.interfaces.ISolution;

public class FonsecaUtil {

	public static String toString(List<Double> l) {
		StringBuilder line = new StringBuilder();
		for (Double d : l) {
			line.append(d);
			line.append(" ");
		}
		return line.toString();
	}
	
	
	public static String toString(Collection<? extends ISolution<?>> set) {
		StringBuilder sb = new StringBuilder();
		for (ISolution<?> s : set) {
			sb.append(FonsecaUtil.toString(s.getObjectives()));
			sb.append("\n");
		}
		return sb.toString();
	}

	
}
